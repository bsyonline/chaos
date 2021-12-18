package com.rolex.alphax.behavioral.visitor;

public class App {
    public static void main(String[] args) {
        Operator operator = new Operator();
        operator.add(new PDFFile("d:/a.pdf"));
        operator.add(new WordFile("d:/b.word"));
        operator.add(new PPTFile("d:/c.ppt"));
        ExtractVisitor extractVisitor = new ExtractVisitor();
        operator.accept(extractVisitor);
        CompressVisitor compressVisitor = new CompressVisitor();
        operator.accept(compressVisitor);
    }

}