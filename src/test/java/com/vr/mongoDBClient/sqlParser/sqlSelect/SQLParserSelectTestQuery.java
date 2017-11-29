package com.vr.mongoDBClient.sqlParser.sqlSelect;

import java.util.ArrayList;
import java.util.List;

import com.vr.mongoDBClient.services.sqlExecuter.sqlParser.sqlSection.FieldSorting;

import lombok.Getter;
import lombok.Setter;

public class SQLParserSelectTestQuery {
    private @Getter @Setter String query;
    private @Getter @Setter List<String> fieldList = new ArrayList<>();
    private @Getter @Setter String target;
    private @Getter @Setter List<FieldSorting> fieldsOrderBy = new ArrayList<>();
}
