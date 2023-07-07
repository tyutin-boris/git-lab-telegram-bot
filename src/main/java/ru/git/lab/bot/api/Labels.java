package ru.git.lab.bot.api;

import lombok.Data;

import java.util.List;

@Data
public class Labels {
    List<Previous> previous;
    List<Current> current;
}
