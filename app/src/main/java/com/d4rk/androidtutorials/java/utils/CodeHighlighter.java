package com.d4rk.androidtutorials.java.utils;

import android.graphics.Color;

import com.amrdeveloper.codeview.CodeView;

import java.util.regex.Pattern;

/**
 * Utility class to apply simple syntax highlighting themes to CodeView.
 */
public class CodeHighlighter {

    private CodeHighlighter() {
        // Utility class
    }

    /**
     * Apply a basic Java highlighting theme to the given CodeView.
     */
    public static void applyJavaTheme(CodeView codeView) {
        codeView.resetSyntaxPatternList();
        codeView.resetHighlighter();

        codeView.addSyntaxPattern(Pattern.compile("//[^\\n]*"), Color.parseColor("#6A9955"));
        codeView.addSyntaxPattern(Pattern.compile("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/"), Color.parseColor("#6A9955"));
        codeView.addSyntaxPattern(Pattern.compile("\"(.*?)\""), Color.parseColor("#CE9178"));
        codeView.addSyntaxPattern(Pattern.compile("\\b(abstract|boolean|break|byte|case|catch|char|class|continue|default|do|double|else|enum|extends|final|finally|float|for|if|implements|import|instanceof|int|interface|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|transient|try|void|volatile|while)\\b"), Color.parseColor("#569CD6"));
        codeView.addSyntaxPattern(Pattern.compile("\\b(\\d+[LFD]?|0x[0-9a-fA-F]+)\\b"), Color.parseColor("#B5CEA8"));

        codeView.reHighlightSyntax();
    }

    /**
     * Apply a basic XML highlighting theme to the given CodeView.
     */
    public static void applyXmlTheme(CodeView codeView) {
        codeView.resetSyntaxPatternList();
        codeView.resetHighlighter();

        codeView.addSyntaxPattern(Pattern.compile("<!--.*?-->", Pattern.DOTALL), Color.parseColor("#808080"));
        codeView.addSyntaxPattern(Pattern.compile("</?[a-zA-Z0-9]+"), Color.parseColor("#800000"));
        codeView.addSyntaxPattern(Pattern.compile("\\b\\w+(?==)"), Color.parseColor("#795E26"));
        codeView.addSyntaxPattern(Pattern.compile("\".*?\"", Pattern.DOTALL), Color.parseColor("#CE9178"));

        codeView.reHighlightSyntax();
    }
}
