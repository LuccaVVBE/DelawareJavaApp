package dtos;

import enums.BoxType;

public record BoxDto(String name, double width, double height, double depth, BoxType boxType, boolean active,
                     double price) {
}
