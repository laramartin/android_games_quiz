package eu.laramartin.gamesquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    List<Quote> listOfQuotes = new ArrayList<>();
    ArrayList threeDiffOptions = new ArrayList();
    ArrayList orderOptions = new ArrayList();

    TextView counterTextView;
    TextView quoteTextView;
    TextView optionOneTextView;
    TextView optionTwoTextView;
    TextView optionThreeTextView;
    Quote optionOneQuote;
    Quote optionTwoQuote;
    Quote optionThreeQuote;
    Quote actualQuote;

    int quotesDisplayed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterTextView = (TextView) findViewById(R.id.counter);
        quoteTextView = (TextView) findViewById(R.id.quote);
        optionOneTextView = (TextView) findViewById(R.id.optionOne);
        optionTwoTextView = (TextView) findViewById(R.id.optionTwo);
        optionThreeTextView = (TextView) findViewById(R.id.optionThree);


        GameQuotes.initQuotes(listOfQuotes);
        shuffleList(listOfQuotes);

        displayQuoteAndOptions();


//        optionOneTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                // check if correct
//                isOptionChosenCorrect(0);
//                Toast.makeText(MainActivity.this, "opt 1 clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        optionTwoTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // check if correct
//                isOptionChosenCorrect(1);
//                Toast.makeText(MainActivity.this, "opt 2 clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        optionThreeTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // check if correct
//                isOptionChosenCorrect(2);
//                Toast.makeText(MainActivity.this, "opt 3 clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
        optionOneTextView.setOnClickListener(choiceOneOnClickListener);
        optionTwoTextView.setOnClickListener(choiceTwoOnClickListener);
        optionThreeTextView.setOnClickListener(choiceThreeOnClickListener);

    }


    final View.OnClickListener choiceOneOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            isOptionChosenCorrect(0);
            Toast.makeText(MainActivity.this, "opt 1 clicked", Toast.LENGTH_SHORT).show();
        }
    };

    final View.OnClickListener choiceTwoOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            isOptionChosenCorrect(1);
            Toast.makeText(MainActivity.this, "opt 2 clicked", Toast.LENGTH_SHORT).show();
        }
    };

    final View.OnClickListener choiceThreeOnClickListener = new View.OnClickListener() {
        public void onClick(final View v){
            isOptionChosenCorrect(2);
            Toast.makeText(MainActivity.this, "opt 3 clicked", Toast.LENGTH_SHORT).show();
        }
    };

//    private int randomNumber(){
//        Random r = new Random();
//        int length = listOfQuotes.size();
//        int num = r.nextInt(length - 1) + 1;
//        return num;
//    }

    private void shuffleList(List items){
        // shuffle existing list and pick 10
        // seed 0 for testing
        Collections.shuffle(items, new Random(0));
        //Collections.shuffle(items);
    }

    private Quote getQuoteFromList(int index){
        return listOfQuotes.get(index);
    }

    private void getOptions(){
        threeDiffOptions.add(quotesDisplayed);
        threeDiffOptions.add(20);
        threeDiffOptions.add(40);
    }

    private void getQuotesFromList(){
        getOptions();
        shuffleList(threeDiffOptions);
        optionOneQuote = getQuoteFromList((Integer) threeDiffOptions.get(0));
        optionTwoQuote = getQuoteFromList((Integer) threeDiffOptions.get(1));
        optionThreeQuote = getQuoteFromList((Integer) threeDiffOptions.get(2));
        addThreeOptionsToList(optionOneQuote, optionTwoQuote, optionThreeQuote);
    }

    private void addThreeOptionsToList(Quote one, Quote two, Quote three){
        orderOptions.add(one);
        orderOptions.add(two);
        orderOptions.add(three);
    }

    private void displayQuoteAndOptions(){
        actualQuote = getQuoteFromList(quotesDisplayed);
        quoteTextView.setText(actualQuote.phrase);

        getQuotesFromList();
        optionOneTextView.setText(optionOneQuote.game);
        optionTwoTextView.setText(optionTwoQuote.game);
        optionThreeTextView.setText(optionThreeQuote.game);
        quotesDisplayed += 1;
        counterTextView.setText(quotesDisplayed + "/10");
    }

    private void isOptionChosenCorrect(int option){
        if (option == 0) {
            if (actualQuote.game != optionOneQuote.game) {
                markOptionChosenAsWrong(optionOneTextView);
                return;
            }
            //return (actualQuote.game == optionOneQuote.game);
        } else if (option == 1){
            if (actualQuote.game != optionTwoQuote.game){
                markOptionChosenAsWrong(optionTwoTextView);
                return;
            }
            //return (actualQuote.game == optionTwoQuote.game);
        } else if (option == 2){
            if (actualQuote.game != optionThreeQuote.game){
                markOptionChosenAsWrong(optionThreeTextView);
                return;
            }
            //return (actualQuote.game == optionThreeQuote.game);
        }
        //markOptionChosenAsWrong(option);
        markOptionChosenAsCorrect(option);

    }

    private void markOptionChosenAsCorrect(int option){
        Quote correct = (Quote) orderOptions.get(option);

    }

    private void markOptionChosenAsWrong(TextView chosen){
        chosen.setBackgroundColor(2);
    }

}
