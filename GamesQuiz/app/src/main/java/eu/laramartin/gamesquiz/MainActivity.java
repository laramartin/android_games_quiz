package eu.laramartin.gamesquiz;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Quote> listOfQuotes = new ArrayList<>();
    Quote correctAnswer;

    TextView counterTextView;
    TextView quoteTextView;
    TextView optionOneTextView;
    TextView optionTwoTextView;
    TextView optionThreeTextView;
    Button nextButton;

    boolean isTurnFinished = false;
    boolean isGameFinished = false;

    int quotesAlreadyDisplayed = 8;
    int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterTextView = (TextView) findViewById(R.id.counter);
        quoteTextView = (TextView) findViewById(R.id.quote);
        optionOneTextView = (TextView) findViewById(R.id.optionOne);
        optionTwoTextView = (TextView) findViewById(R.id.optionTwo);
        optionThreeTextView = (TextView) findViewById(R.id.optionThree);
        nextButton = (Button) findViewById(R.id.buttonNext);
        nextButton.setText(R.string.next);

        GameQuotes.initQuotes(listOfQuotes);
        shuffleList(listOfQuotes);

        displayQuoteAndAnswers();

        optionOneTextView.setOnClickListener(choiceOneOnClickListener);
        optionTwoTextView.setOnClickListener(choiceTwoOnClickListener);
        optionThreeTextView.setOnClickListener(choiceThreeOnClickListener);
        nextButton.setOnClickListener(nextButtonOnClickListener);
    }

    final View.OnClickListener choiceOneOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (!isTurnFinished && !isGameFinished){
                isOptionChosenCorrect(0);
                isTurnFinished = true;
            }
        }
    };

    final View.OnClickListener choiceTwoOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (! isTurnFinished && !isGameFinished){
                isOptionChosenCorrect(1);
                isTurnFinished = true;
            }
        }
    };

    final View.OnClickListener choiceThreeOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (!isTurnFinished && !isGameFinished){
                isOptionChosenCorrect(2);
                isTurnFinished = true;
            }
        }
    };

    final View.OnClickListener nextButtonOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            if (isGameFinished) {
                resetGame();
            } else if (isTurnFinished) {
                beginNextTurn();
            }
        }
    };

    private void shuffleList(List items){
        // seed 0 for testing
//        Collections.shuffle(items, new Random(0));
        Collections.shuffle(items);
    }

    private List<Quote> getOptions(){
        List<Quote> options = new ArrayList<>();
        options.add(listOfQuotes.get(quotesAlreadyDisplayed));
        options.add(listOfQuotes.get(quotesAlreadyDisplayed + 20));
        options.add(listOfQuotes.get(quotesAlreadyDisplayed + 40));
        return options;
    }

    private List<Quote> getThreeQuotesFromListToDisplayAsAnswer(){
        List<Quote> options = getOptions();
        shuffleList(options);
        return options;
    }

    private void displayQuoteAndAnswers(){
        if (quotesAlreadyDisplayed == 10){
            isGameFinished = true;
            finalGameDisplay();
            return;
        }
        correctAnswer = listOfQuotes.get(quotesAlreadyDisplayed);
        quoteTextView.setText(correctAnswer.phrase);

        List<Quote> answers = getThreeQuotesFromListToDisplayAsAnswer();
        optionOneTextView.setText(answers.get(0).game);
        optionTwoTextView.setText(answers.get(1).game);
        optionThreeTextView.setText(answers.get(2).game);
        quotesAlreadyDisplayed += 1;
        String counterText = Integer.toString(quotesAlreadyDisplayed) +
                this.getResources().getString(R.string.totalTurns);
        counterTextView.setText(counterText);
    }

    private String getSelectedQuote(int option){
        switch (option) {
            case 0:
                return optionOneTextView.getText().toString();
            case 1:
                return optionTwoTextView.getText().toString();
            case 2:
                return optionThreeTextView.getText().toString();
            default:
                return "";
        }
    }

    private TextView getSelectedTextView(int option){
        switch (option) {
            case 0:
                return optionOneTextView;
            case 1:
                return optionTwoTextView;
            case 2:
                return optionThreeTextView;
            default:
                return null;
        }
    }

    private void isOptionChosenCorrect(int option){
        TextView selectedTextView = getSelectedTextView(option);
        if (correctAnswer.game != getSelectedQuote(option)){
            markOptionChosenAsWrong(selectedTextView);
            return;
        }
        markOptionChosenAsCorrect(selectedTextView);
        correctAnswers += 1;
    }

    private void markOptionChosenAsCorrect(TextView chosen){
        chosen.setBackgroundColor(Color.parseColor("#49C684"));
        makeNextButtonVisible();
        checkIfTurnHasFinished();
    }

    private void markOptionChosenAsWrong(TextView chosen){
        chosen.setBackgroundColor(Color.parseColor("#F55E7A"));
        TextView correctAnswerTextView = getTextViewCorrectAnswer();
        correctAnswerTextView.setBackgroundColor(Color.parseColor("#49C684"));
        makeNextButtonVisible();
        checkIfTurnHasFinished();
    }

    private TextView getTextViewCorrectAnswer() {
        if (optionOneTextView.getText().toString().equals(correctAnswer.game)) {
            return optionOneTextView;
        }
        if (optionTwoTextView.getText().toString().equals(correctAnswer.game)) {
            return optionTwoTextView;
        }
        if (optionThreeTextView.getText().toString().equals(correctAnswer.game)) {
            return optionThreeTextView;
        }
        return null;
    }

    private void checkIfTurnHasFinished(){
        if (isTurnFinished) {
            beginNextTurn();
        }
    }

    private void finalGameDisplay(){
        notDisplayCounterAndAnswers();
        finalGameResults();
        nextButton.setText(R.string.reset);
        makeNextButtonVisible();
    }

    private void beginNextTurn(){
        isTurnFinished = false;
        correctAnswer = null;
        resetAnswersBackgroundColor();
        makeNextButtonInvisible();
        displayQuoteAndAnswers();
    }

    private void makeNextButtonVisible(){
        nextButton.setVisibility(View.VISIBLE);
    }

    private void makeNextButtonInvisible(){
        nextButton.setVisibility(View.INVISIBLE);
    }

    private void resetAnswersBackgroundColor(){
        optionOneTextView.setBackgroundColor(0x00000000);
        optionTwoTextView.setBackgroundColor(0x00000000);
        optionThreeTextView.setBackgroundColor(0x00000000);
    }

    private void notDisplayCounterAndAnswers(){
        optionOneTextView.setText("");
        optionTwoTextView.setText("");
        optionThreeTextView.setText("");
        counterTextView.setText("");
    }
    private void finalGameResults(){
        String finalGameString = this.getResources().getString(R.string.correctAnswer) +
                "\n\n" +
                String.format("%d", correctAnswers) +
                this.getResources().getString(R.string.totalTurns);
        quoteTextView.setText(finalGameString);
    }

    private void resetGame(){
        beginNextTurn();
        isTurnFinished = false;
        isGameFinished = false;
        correctAnswers = 0;
        quotesAlreadyDisplayed = 0;
        shuffleList(listOfQuotes);
        displayQuoteAndAnswers();
        nextButton.setText(R.string.next);
        makeNextButtonInvisible();
    }

}
