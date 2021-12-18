package com.rolex.alphax.behavioral.visitor;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PPTFile implements ResourceFile {

    private String filePath;

    public PPTFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}