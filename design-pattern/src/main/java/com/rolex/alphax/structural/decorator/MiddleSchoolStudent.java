/*
 * Copyright (C) 2020 bsyonline
 */
package com.rolex.alphax.structural.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @author rolex
 * @since 2020
 */
@Slf4j
public class MiddleSchoolStudent implements Student {

    private Student student;

    public MiddleSchoolStudent(Student student) {
        this.student = student;
    }

    @Override
    public void exam() {
        student.exam();
        log.info("中学考试");
    }
}
