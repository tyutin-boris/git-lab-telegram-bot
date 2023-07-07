package ru.git.lab.bot.mappers;

public interface AbstractMapper<E, D> extends ToDtoMapper<E, D>, ToEntityMapper<D, E> {
}
