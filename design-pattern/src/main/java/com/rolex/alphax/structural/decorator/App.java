/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.decorator;

/**
 * @author rolex
 * @since 2020
 */
public class App {
    public static void main(String[] args) {
        PrimarySchoolStudent primarySchoolStudent = new PrimarySchoolStudent();
        primarySchoolStudent.exam();
        CollageStudent collageStudent = new CollageStudent(new MiddleSchoolStudent(primarySchoolStudent));
        collageStudent.exam();
    }
}
