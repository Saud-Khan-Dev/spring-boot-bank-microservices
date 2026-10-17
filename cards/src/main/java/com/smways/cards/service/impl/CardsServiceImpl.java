package com.smways.cards.service.impl;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.smways.cards.constants.CardsConstants;
import com.smways.cards.dto.CardsDto;
import com.smways.cards.entity.Cards;
import com.smways.cards.exception.CardAlreadyExistsException;
import com.smways.cards.exception.ResourceNotFoundException;
import com.smways.cards.mapper.CardsMapper;
import com.smways.cards.repository.CardsRepository;
import com.smways.cards.service.ICardsService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> existingCard = cardsRepository.findByMobileNumber(mobileNumber);
        if (existingCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobile number " + mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard = new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobile number", mobileNumber)
        );
        return CardsMapper.toDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber())
            .orElseThrow(() -> new ResourceNotFoundException("Card", "card number", cardsDto.getCardNumber())
        );
        CardsMapper.toEntity(cardsDto, cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Card", "mobile number", mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }

}
