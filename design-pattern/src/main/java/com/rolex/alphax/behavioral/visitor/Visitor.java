package com.rolex.alphax.behavioral.visitor;

public interface Visitor {
    void visit(PDFFile pdfFile);

    void visit(PPTFile pdfFile);

    void visit(WordFile pdfFile);
}