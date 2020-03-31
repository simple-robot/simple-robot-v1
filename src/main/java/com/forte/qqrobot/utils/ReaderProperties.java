package com.forte.qqrobot.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 语言类Properties
 * @author ForteScarlet
 */
public class ReaderProperties extends Properties {

    /** 使用流读取的时候，默认使用utf-8编码读取 */
    private Charset defaultCharset;

    public ReaderProperties(){
        defaultCharset = StandardCharsets.UTF_8;
    }

    public ReaderProperties(String charset){
        defaultCharset = Charset.forName(charset);
    }

    public ReaderProperties(Charset charset){
        defaultCharset = charset;
    }


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
        load(new BufferedReader(new InputStreamReader(inputStream, defaultCharset)));
    }
    /**
     * override this load method
     */
    public void load(InputStream inputStream, String charset) throws IOException {
        load(new BufferedReader(new InputStreamReader(inputStream, charset)));
    }


}
