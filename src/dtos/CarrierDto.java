package dtos;

public record CarrierDto(String name, String email, String phone, String image, boolean active, int amountOfCharacters,
                         boolean numOnly, String prefix) {

}