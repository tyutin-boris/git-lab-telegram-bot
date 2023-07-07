package ru.git.lab.bot.mappers;

import java.util.List;

public interface ToEntityMapper<D, E> {
    E toEntity(D d);

    List<E> toEntity(List<D> d);
}
