package com.smways.cards.mapper;

import com.smways.cards.dto.CardsDto;
import com.smways.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto toDto(Cards cards, CardsDto cardsDto) {
        if (cards == null) {
            return null;
        }

        if (cardsDto == null) {
            cardsDto = new CardsDto();
        }

        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());

        return cardsDto;
    }

    public static Cards toEntity(CardsDto cardsDto, Cards cards) {
        if (cardsDto == null) {
            return null;
        }

        if (cards == null) {
            cards = new Cards();
        }

        cards.setMobileNumber(cardsDto.getMobileNumber());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setCardType(cardsDto.getCardType());
        cards.setTotalLimit(cardsDto.getTotalLimit());
        cards.setAmountUsed(cardsDto.getAmountUsed());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());

        return cards;
    }

}
