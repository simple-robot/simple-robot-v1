package com.forte.qqrobot.utils;

import java.io.*;
import java.util.Properties;

/**
 * 语言类Properties
 * @author ForteScarlet
 */
public class ReaderProperties extends Properties {

    /**
     * override this load method
     */
    @Override
    public void load(Reader reader) throws IOException {
        super.load(new BufferedReader(reader));
    }
    /**
     * override this load method
     */
    @Override
    public void load(InputStream inputStream) throws IOException {
        load(new BufferedReader(new InputStreamReader(inputStream)));
    }


}
