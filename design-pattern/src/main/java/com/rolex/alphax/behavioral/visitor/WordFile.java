package com.rolex.alphax.behavioral.visitor;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WordFile implements ResourceFile {

    private String filePath;

    public WordFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}