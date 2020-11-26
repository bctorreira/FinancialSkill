package org.uvigo.esei.dm.financialskill.ui;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.uvigo.esei.dm.financialskill.R;
import org.uvigo.esei.dm.financialskill.core.Expense;
import org.uvigo.esei.dm.financialskill.db.ExpenseFacade;

import java.util.Locale;

public class ExpenseCursorAdapter extends CursorAdapter {
    private ExpenseFacade expenseFacade;

    public ExpenseCursorAdapter(Context context, Cursor c, ExpenseFacade expenseFacade) {
        super(context, c, false);
        this.expenseFacade = expenseFacade;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_view_expense, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView TEXTVIEW_CATEGORY = view.findViewById(R.id.textViewCategory);
        TextView TEXTVIEW_CONCEPT = view.findViewById(R.id.textViewConcept);
        TextView TEXTVIEW_QUANTITY = view.findViewById(R.id.textViewQuantity);
        final Expense expense = ExpenseFacade.readExpense(cursor);
        TEXTVIEW_CATEGORY.setText(expense.getCategory());
        TEXTVIEW_CONCEPT.setText(expense.getConcept());
        TEXTVIEW_QUANTITY.setText(String.format(Locale.US, "%.2f", expense.getQuantity()));
    }
}
