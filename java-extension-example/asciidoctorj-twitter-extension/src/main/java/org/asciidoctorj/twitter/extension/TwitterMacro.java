package org.asciidoctorj.twitter.extension;

import java.util.HashMap;
import java.util.Map;

import org.asciidoctor.ast.AbstractBlock;
import org.asciidoctor.ast.Inline;
import org.asciidoctor.extension.InlineMacroProcessor;

public class TwitterMacro extends InlineMacroProcessor {

    public TwitterMacro(String macroName, Map<String, Object> config) {
        super(macroName, config);
    }

    @Override
    protected Object process(AbstractBlock parent, String twitterHandle, Map<String, Object> attributes) {

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
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("type", ":link");
        options.put("target", twitterLink);

        // Create the 'anchor' node:
        Inline inlineTwitterLink = createInline(parent, "anchor", twitterLinkText, attributes, options);

        // Convert to String value:
        return inlineTwitterLink.convert();
    }
}
