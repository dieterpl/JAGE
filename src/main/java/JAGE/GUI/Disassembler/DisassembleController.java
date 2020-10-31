//See: https://github.com/FXMisc/RichTextFX/blob/master/richtextfx-demos/src/main/java/org/fxmisc/richtext/demo/JavaKeywordsDemo.java
/*
Copyright (c) 2013-2017, Tomas Mikula and contributors
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

Modifications Copyright (c) 2020 P. Dieterich
*/

package JAGE.GUI.Disassembler;

import JAGE.GUI.MemoryView.MemoryGUIInterface;
import JAGE.GUI.GUITools.RegisterConverter;
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


public class DisassembleController implements Initializable {

    /**
     * Common syntax for language
     */
    private static final String[] KEYWORDS = new String[]{
            "NOP"
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

    private static StyleSpans<Collection<String>> computeSyntaxHighlighting(String text) {
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

    private Task<StyleSpans<Collection<String>>> computeSyntaxHighlightingAsync() {
        String text = codeArea.getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeSyntaxHighlighting(text);
            }
        };
        executor.execute(task);
        return task;
    }

    private void applySyntaxHighlighting(StyleSpans<Collection<String>> highlighting) {
        codeArea.setStyleSpans(0, highlighting);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillRegisterTable();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        Subscription code = codeArea
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> codeArea.setStyleSpans(0, computeSyntaxHighlighting(codeArea.getText())));

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
        Supervisor.getInstance().restartEmulator();
    }
}
