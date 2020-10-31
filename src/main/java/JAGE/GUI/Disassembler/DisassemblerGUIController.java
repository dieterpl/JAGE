package JAGE.GUI.Disassembler;


import JAGE.GUI.MemoryView.MemoryGUIInterface;
import JAGE.GUI.RegisterConverter;
import JAGE.processor.Registers.*;
import JAGE.supervisor.Supervisor;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DisassemblerGUIController implements Initializable {

    private static final String[] KEYWORDS = new String[]{
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );
    private static final String sampleCode = String.join("\n", new String[]{
            "package com.example;",
            "",
            "import java.util.*;",
            "",
            "public class Foo extends Bar implements Baz {",
            "",
            "    /*",
            "     * multi-line comment",
            "     */",
            "    public static void main(String[] args) {",
            "        // single-line comment",
            "        for(String arg: args) {",
            "            if(arg.length() != 0)",
            "                System.out.println(arg);",
            "            else",
            "                System.err.println(\"Warning: empty string as argument\");",
            "        }",
            "    }",
            "",
            "}"
    });
    @FXML
    public VBox vbox;
    @FXML
    public SplitPane pane;
    @FXML
    CodeArea codeArea;
    private ExecutorService executor;
    @FXML
    private TableView<DisassemblerTableGUIInterface> disTable;
    @FXML
    private TableView<RegisterConverter> registerTable;
    @FXML
    private TableColumn<RegisterConverter, String> registerCol;
    @FXML
    private TableColumn<RegisterConverter, String> valueCol;
    @FXML
    private TableView<MemoryGUIInterface> dataTable;
    @FXML
    private TableColumn<MemoryGUIInterface, String> addressCol;
    @FXML
    private TableColumn<MemoryGUIInterface, String> ramValueCol;
    @FXML
    private TableColumn<MemoryGUIInterface, String> ramValueHCol;

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applyHighlighting(StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillRegisterTable();
        // add line numbers to the left of area
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        // recompute the syntax highlighting 500 ms after user stops editing area
        Subscription cleanupWhenNoLongerNeedIt = codeArea

                // plain changes = ignore style changes that are emitted when syntax highlighting is reapplied
                // multi plain changes = save computation by not rerunning the code multiple times
                //   when making multiple changes (e.g. renaming a method at multiple parts in file)
                .multiPlainChanges()

                // do not emit an event until 500 ms have passed since the last emission of previous stream
                .successionEnds(Duration.ofMillis(500))

                // run the following code block when previous stream emits an event
                .subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));

        // when no longer need syntax highlighting and wish to clean up memory leaks
        // run: `cleanupWhenNoLongerNeedIt.unsubscribe();`

        codeArea.replaceText(0, 0, Supervisor.getInstance().generateDissasembly());

    }

    public void fillRegisterTable() {
        registerCol.setCellValueFactory(new PropertyValueFactory<RegisterConverter, String>("name"));
        valueCol.setCellValueFactory(new PropertyValueFactory<RegisterConverter, String>("value"));
        registerTable.getItems().setAll(getRegisterItemsToAdd());

    }

    private List<RegisterConverter> getRegisterItemsToAdd() {
        List<RegisterConverter> registerData = new ArrayList<>();
        registerData.add(new RegisterConverter(RegisterA.getInstance()));
        registerData.add(new RegisterConverter(RegisterB.getInstance()));
        registerData.add(new RegisterConverter(RegisterC.getInstance()));
        registerData.add(new RegisterConverter(RegisterD.getInstance()));
        registerData.add(new RegisterConverter(RegisterE.getInstance()));
        registerData.add(new RegisterConverter(RegisterF.getInstance()));
        registerData.add(new RegisterConverter(RegisterH.getInstance()));
        registerData.add(new RegisterConverter(RegisterL.getInstance()));
        registerData.add(new RegisterConverter(RegisterAF.getInstance()));
        registerData.add(new RegisterConverter(RegisterBC.getInstance()));
        registerData.add(new RegisterConverter(RegisterDE.getInstance()));
        registerData.add(new RegisterConverter(RegisterHL.getInstance()));
        registerData.add(new RegisterConverter(RegisterPC.getInstance()));
        registerData.add(new RegisterConverter(RegisterSP.getInstance()));
        return registerData;
    }


    public void update(ActionEvent actionEvent) {
        Supervisor.getInstance().startEmulator();
    }
}
