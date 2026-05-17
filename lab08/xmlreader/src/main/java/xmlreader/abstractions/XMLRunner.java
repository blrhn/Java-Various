package xmlreader.abstractions;

import xmlreader.jaxp.models.ChannelJAXP;

import java.io.File;

public interface XMLRunner {
    ChannelJAXP run(File file) throws Exception;
}