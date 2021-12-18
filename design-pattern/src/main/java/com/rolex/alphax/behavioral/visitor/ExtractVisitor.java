package com.rolex.alphax.behavioral.visitor;

public class ExtractVisitor implements Visitor {

    @Override
    public void visit(PDFFile pdfFile) {
        System.out.println("extract " + pdfFile.getFilePath());
    }

    @Override
    public void visit(PPTFile pptFile) {
        System.out.println("extract " + pptFile.getFilePath());
    }

    @Override
    public void visit(WordFile wordFile) {
        System.out.println("extract " + wordFile.getFilePath());
    }
}