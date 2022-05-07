package com.turkcell.rentacar.business.constants.messages;

public class BusinessMessages {
    private BusinessMessages() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SUCCESSFULLY_ADDED = "Successfully added.";
    public static final String SUCCESSFULLY_FOUND = "Successfully found.";
    public static final String SUCCESSFULLY_DELETED = "Successfully deleted.";
    public static final String SUCCESSFULLY_UPDATED = "Successfully updated.";
    public static final String ALREADY_EXIST = "Data is already exist.";

    public static final String NOT_FOUND = "Data not found.";
    public static final String INVALID_PARAMETER = "Invalid parameter to fetch data.";
    public static final String SUCCESSFULLY_LISTED = "Successfully listed.";
    public static final String RENT_DATES_NOT_CORRECT = "Dates invalid for save.";
    public static final String RETURN_DATE_NOT_CORRECT = "Check out your return date.";
    public static final String END_KILOMETER_NOT_CORRECT = "Rent end kilometer can not be less than rent start kilometer.";
    public static final String NO_NEED_FOR_EXTRA_PAYMENT = "There is no need for an extra payment.";
    public static final String CAR_IS_UNDER_MAINTENANCE = "This car is under maintenance.";
    public static final String PAYMENT_UNSUCCESSFULLY = "Payment failed.";
    public static final String RENT_ENDED = "Rent finished successfully.";
    public static final String CAR_IS_CURRENTLY_RENTED = "This car can not rent for now. Already rented.";
    public static final String RENT_ALREADY_ENDED = "Rent already ended. You should not pay anymore.";
    public static final String NEED_EXTRA_PAYMENT = "There is no need to payment extra.";


}


