package org.uvigo.esei.dm.financialskill.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.uvigo.esei.dm.financialskill.R;
import org.uvigo.esei.dm.financialskill.core.Expense;
import org.uvigo.esei.dm.financialskill.core.FSApplication;
import org.uvigo.esei.dm.financialskill.db.ExpenseFacade;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ExpenseFacade EXPENSEFACADE;
    private ExpenseCursorAdapter EXPENSECURSORADAPTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EXPENSEFACADE = new ExpenseFacade(((FSApplication)getApplication()).getDbManager());
        Cursor cursor = EXPENSEFACADE.getExpenses();
        EXPENSECURSORADAPTER = new ExpenseCursorAdapter(MainActivity.this, cursor, EXPENSEFACADE);

        ListView LISTVIEWEXPENSES = findViewById(R.id.listViewExpenses);
        LISTVIEWEXPENSES.setAdapter(EXPENSECURSORADAPTER);

        TextView TEXTVIEW_BALANCE = findViewById(R.id.textViewBalanceTotal);
        Button BUTTON_EXPENSE = findViewById(R.id.buttonExpense);
        Button BUTTON_INCOME = findViewById(R.id.buttonIncome);

        BUTTON_EXPENSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EXPENSEFACADE.removeAllExpenses();
                EXPENSECURSORADAPTER.swapCursor(EXPENSEFACADE.getExpenses());
                Double balance = EXPENSEFACADE.getTotalBalance();
                TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance));
            }
        });

        BUTTON_INCOME.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expense expense = new Expense("concepto1", 2.69, "FOOD", "Descripcion1");
                EXPENSEFACADE.addExpense(expense);
                EXPENSECURSORADAPTER.swapCursor(EXPENSEFACADE.getExpenses());
                Double balance = EXPENSEFACADE.getTotalBalance();
                TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance));
            }
        });

        // EXPENSE ADDITION TEST
        Expense expense1 = new Expense("concepto1", 2.69, "FOOD", "Descripcion1");
        Expense expense2 = new Expense("concepto2", 4.00, "HOME", "Descripcion2");
        Expense expense3 = new Expense("concepto3", 1.31, "HEALTH", "Descripcion3");
        EXPENSEFACADE.addExpense(expense1);
        EXPENSEFACADE.addExpense(expense2);
        EXPENSEFACADE.addExpense(expense3);
        //SWAP CURSOR TO UPDATE VIEW
        EXPENSECURSORADAPTER.swapCursor(EXPENSEFACADE.getExpenses());
        
        Double balance = EXPENSEFACADE.getTotalBalance();

        TEXTVIEW_BALANCE.setText(String.format(Locale.US, "%.2f", balance));
    }
}