package ru.git.lab.bot.mappers;

import java.util.List;

public interface ToDtoMapper<E, D> {
    D toDto(E e);

    List<D> toDto(List<E> e);
}
