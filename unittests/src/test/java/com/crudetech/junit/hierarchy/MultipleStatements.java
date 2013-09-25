package com.crudetech.junit.hierarchy;

import org.junit.runners.model.Statement;

import static java.util.Arrays.asList;

class MultipleStatements extends Statement {
    private final Iterable<Statement> statements;

    MultipleStatements(Statement... statements) {
        this.statements = asList(statements);
    }

    @Override
    public void evaluate() throws Throwable {
        for (Statement statement : statements) {
            statement.evaluate();
        }
    }
}
