package com.indushospitals.model;


import com.indushospitals.validator.RuleCommand;
import com.indushospitals.validator.ValidatedObservableField;
import com.indushospitals.validator.rules.MinimumLengthRule;
import com.indushospitals.validator.rules.RegexRule;

/**
 * Created by think360 on 14/06/17.
 */

public class ValidationViewModel {
    //public final ObservableBoolean passwordVisible = new ObservableBoolean(false);

    public final ValidatedObservableField<String> userName = new ValidatedObservableField<>("",
            new RuleCommand.Builder<String>()
                    .withRule(new RegexRule("^[\\p{L} .'-]+$", "Only letters")) // THE ORDER IS IMPORTANT!
                    .withRule(new MinimumLengthRule(3, "Three or more characters"))
                  //  .withRule(new MaximumLengthRule(12, "No more then twelve characters"))
                    .build());
    public final ValidatedObservableField<String> userAge = new ValidatedObservableField<>("",
            new RuleCommand.Builder<String>()
                    .withRule(new RegexRule("(99)|(0*\\d{1,2})", "Age not valid"))
                   // .withRule(new MinimumLengthRule(1, "Age not"))
                     // .withRule(new MaximumLengthRule(2, "Age not valid"))
                    .build());
    public final ValidatedObservableField<String> userEmail = new ValidatedObservableField<>("",
            new RuleCommand.Builder<String>()
                      .withRule(new RegexRule("[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", "Email not valid")) // THE ORDER IS IMPORTANT!
                    // .withRule(new MinimumLengthRule(1, "Age not"))
                    //.withRule(new MaximumLengthRule(2, "Age not valid"))
                    .build());

    public final ValidatedObservableField<String> userContact = new ValidatedObservableField<>("",
            new RuleCommand.Builder<String>()
                    .withRule(new RegexRule("^[7-9][0-9]{9}$", "Mobile No is not valid")) // THE ORDER IS IMPORTANT!
                    // .withRule(new MinimumLengthRule(1, "Age not"))
                    //.withRule(new MaximumLengthRule(2, "Age not valid"))
                    .build());

    public final ValidatedObservableField<String> userAdd = new ValidatedObservableField<>("",
            new RuleCommand.Builder<String>()
                    .withRule(new RegexRule("^[\\p{L}0-9\\s]*$", "Address not valid")) // THE ORDER IS IMPORTANT!
                     .withRule(new MinimumLengthRule(3, "Enter more than 3 character"))
                    //.withRule(new MaximumLengthRule(2, "Age not valid"))
                    .build());
    public final ValidatedObservableField<String> userComm = new ValidatedObservableField<>("",
            new RuleCommand.Builder<String>()
                    .withRule(new RegexRule("^[\\p{L}0-9\\s]*$", "Comment not valid")) // THE ORDER IS IMPORTANT!
                    .withRule(new MinimumLengthRule(3, "Enter more than 3 character"))
                    //.withRule(new MaximumLengthRule(2, "Age not valid"))
                    .build());

    /* public final ValidatedObservableField<String> password = new ValidatedObservableField<>("",
            new RuleCommand.Builder<String>()
                    .withRule(new RegexRule("[\\S]+", "Whitespace characters not allowed")) // THE ORDER IS IMPORTANT!
                    .withRule(new RegexRule(".*[A-Z]+.*", "Must contain capital letters"))
                    .withRule(new RegexRule(".*[0-9]+.*", "Must contain digits"))
                    .withRule(new RegexRule(".*[a-z]+.*", "Must contain small letters"))
                    .withRule(new MinimumLengthRule(8, "Eight or more characters"))
                    .withRule(new MaximumLengthRule(16, "No more then sixteen characters"))
                    .build());

    public void onViewClick(View view) {
        if (userName.isValid() && password.isValid()) {
            Toast.makeText(view.getContext(), "ALL OK !", Toast.LENGTH_LONG).show();
        }
    }*/
}
