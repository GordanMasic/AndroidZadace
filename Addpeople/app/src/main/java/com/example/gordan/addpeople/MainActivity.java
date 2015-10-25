package com.example.gordan.addpeople;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static PersonList personList = new PersonList();
    private EditText mNameText;
    private EditText mSurnameText;
    private Button mAddPersonButton;
    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private RadioButton sortByNameButton;
    private RadioButton sortBySurnameButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        mNameText = (EditText) findViewById(R.id.insert_name);
        mSurnameText = (EditText) findViewById(R.id.insert_surname);
        mAddPersonButton = (Button) findViewById(R.id.add_button);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        personAdapter = new PersonAdapter(personList);
        recyclerView.setAdapter(personAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAddPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable name = mNameText.getText();
                Editable surname = mSurnameText.getText();
                if (name.toString() != null && !name.toString().equals("") && surname.toString() != null && !surname.toString().equals("")) {
                    personList.addPerson(name, surname);
                    personAdapter.notifyDataSetChanged();
                    mNameText.setText("");
                    mSurnameText.setText("");
                    updateUI();
                } else {
                    Toast.makeText(MainActivity.this, "Please fill fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sortByNameButton = (RadioButton) findViewById(R.id.radioButton1);
        sortBySurnameButton = (RadioButton) findViewById(R.id.radioButton2);
        sortByNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortBySurnameButton.setChecked(!sortByNameButton.isChecked());
                if(personList.getSize() != 0){
                    personList.sortByName();
                    updateUI();
                }
            }
        });
        sortBySurnameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByNameButton.setChecked(!sortBySurnameButton.isChecked());
                if(personList.getSize() != 0){
                    personList.sortBySurname();
                    updateUI();
                }
            }
        });
    }

    private class PersonView extends RecyclerView.ViewHolder{
        private TextView nameText;
        private TextView surnameText;
        private TextView dateText;
        private Button removeButton;
        private Button editButton;


        public PersonView(View itemView) {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.person_name);
            surnameText = (TextView) itemView.findViewById(R.id.person_surname);
            dateText = (TextView) itemView.findViewById(R.id.person_date);
            removeButton = (Button) itemView.findViewById(R.id.remove_button);
            editButton = (Button) itemView.findViewById(R.id.edit_button);


        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonView> {

        private PersonList personList;
        private EditText editNameText;
        private EditText editSurnameText;
        private Button editButton1;

        public PersonAdapter(PersonList personList){
            this.personList = personList;
        }


        @Override
        public PersonView onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

            View view = layoutInflater.inflate(R.layout.person_layout, parent, false);
            return new PersonView(view);
        }

        @Override
        public void onBindViewHolder(final PersonView holder, int position) {
            final PersonModel person = personList.getPerson(position);

            holder.nameText.setText(person.getName());
            holder.surnameText.setText(person.getSurname());
            holder.dateText.setText(person.getDateAdded().toString());
            holder.removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    personList.removePerson(person.getID());
                    updateUI();
                }
            });

            LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            final Dialog commentDialog = new Dialog(MainActivity.this);
            holder.editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    commentDialog.setContentView(R.layout.edit_person_popup_layout);
                    editNameText = (EditText) commentDialog.findViewById(R.id.edit_name);
                    editSurnameText = (EditText) commentDialog.findViewById(R.id.edit_surname);
                    editNameText.setText(person.getName());
                    editSurnameText.setText(person.getSurname());
                    editButton1 = (Button) commentDialog.findViewById(R.id.edit_button1);
                    editButton1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (editNameText.getText().toString() != null && !editNameText.getText().toString().equals("") && editSurnameText.getText().toString() != null && !editSurnameText.getText().toString().equals("")) {
                                holder.nameText.setText(editNameText.getText());
                                holder.surnameText.setText(editSurnameText.getText());
                                personList.updatePerson(person.getID(), editNameText.getText().toString(), editSurnameText.getText().toString());
                                updateUI();
                                commentDialog.dismiss();
                            } else {
                                Toast.makeText(MainActivity.this, "Please fill fields!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    commentDialog.show();
                    updateUI();
                }
            });
        }

        @Override
        public int getItemCount() {
            return personList.getSize();
        }

    }

    private void updateUI() {
        personAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(personAdapter);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Do you want to exit?");

        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                MainActivity.this.finish();
            }
        });

        alert.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                    }
                });

        alert.show();

    }
}
