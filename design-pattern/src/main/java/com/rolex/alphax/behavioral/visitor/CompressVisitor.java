/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.behavioral.visitor;

/**
 * @author rolex
 * @since 2020
 */
public class CompressVisitor implements Visitor {

    @Override
    public void visit(PDFFile pdfFile) {
        System.out.println("compress " + pdfFile.getFilePath());
    }

    @Override
    public void visit(PPTFile pptFile) {
        System.out.println("compress " + pptFile.getFilePath());
    }

    @Override
    public void visit(WordFile wordFile) {
        System.out.println("compress " + wordFile.getFilePath());
    }
}
