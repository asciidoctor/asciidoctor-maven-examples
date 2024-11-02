package org.asciidoctorj.twitter.extension;

import org.asciidoctor.ast.PhraseNode;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.HashMap;
import java.util.Map;

public class TwitterMacro extends InlineMacroProcessor {

    public TwitterMacro(String macroName) {
        super(macroName);
    }

    @Override
    public PhraseNode process(StructuralNode parent, String target, Map<String, Object> attributes) {
        String twitterLink;
        String twitterLinkText;
        if (target == null || target.isEmpty()) {
            twitterLink = "https://www.twitter.com/";
            twitterLinkText = "Twitter";
        } else {
            twitterLink = "https://www.twitter.com/" + target;
            // Prepend twitterHandle with @ as text link:
            twitterLinkText = "@" + target;
        }

        // Define options for an 'anchor' element:
        Map<String, Object> options = new HashMap<>();
        options.put("type", ":link");
        options.put("target", twitterLink);

        // Create the 'anchor' node:
        return createPhraseNode(parent, "anchor", twitterLinkText, attributes, options);
    }
}
