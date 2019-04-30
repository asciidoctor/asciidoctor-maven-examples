package org.asciidoctorj.twitter.extension;

import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.ast.PhraseNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.HashMap;
import java.util.Map;

public class TwitterMacro extends InlineMacroProcessor {

    public TwitterMacro(String macroName) {
        super(macroName);
    }

    @Override
    public Object process(ContentNode contentNode, String twitterHandle, Map<String, Object> attributes) {

        String twitterLink;
        String twitterLinkText;
        if (twitterHandle == null || twitterHandle.isEmpty()) {
            twitterLink = "http://www.twitter.com/";
            twitterLinkText = "Twitter";
        } else {
            twitterLink = "http://www.twitter.com/" + twitterHandle;
            // Prepend twitterHandle with @ as text link:
            twitterLinkText = "@" + twitterHandle;
        }

        // Define options for an 'anchor' element:
        Map<String, Object> options = new HashMap<>();
        options.put("type", ":link");
        options.put("target", twitterLink);

        // Create the 'anchor' node:
        PhraseNode inlineTwitterLink = createPhraseNode(contentNode, "anchor", twitterLinkText, attributes, options);

        // Convert to String value:
        return inlineTwitterLink.convert();
    }
}
