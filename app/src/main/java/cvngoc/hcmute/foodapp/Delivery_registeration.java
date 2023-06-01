package cvngoc.hcmute.foodapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import cvngoc.hcmute.foodapp.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;

public class Delivery_registeration extends AppCompatActivity {

    String[] Maharashtra = {"Mumbai", "Pune", "Aurangabad"};
    String[] Gujarat = {"Ahemdabad", "Rajkot", "Surat"};

    String[] HoChiMinh = {"District 1", "District 2", "District 3", "District 4", "District 5", "District 6", "District 7", "District 8",
            "District 9", "District 10", "District 11", "District 12","Thu Duc", "Binh Thanh", "Go Vap", "Tan Binh", "Tan Phu", "Phu Nhuan", "Binh Tan"};

    String[] Mumbai = {"Churchgate", "Marine Lines", "Charni Road", "Grant Road", "Mumbai Central", "Mahalakshmi", "Lower Parel", "Prabhadevi",
            "Dadar", "Matunga", "Mahim", "Bandra", "Khar", "Santacruz", "Vile Parle", "Andheri", "Jogeshwari", "Ram Mandir",
            "Goregaon", "Malad", "Kandivai", "Borivali", "Dahisar", "MiraRoad", "Bhayander", "Naigaon", "Vasai Road", "Nalla Sopara", "Virar"};


    String[] Pune = {"Hinjewadi", "Wagholi", " Ambegaon", "Undri", "Katraj"};
    String[] Aurangabad = {"Aarif Colony", "Baiji Pura", "Balaji Nagar", "Angoori Bagh"};


    TextInputLayout Fname, Lname, Pass, cfpass, mobileno, houseno, area, postcode, Email;
    Spinner Districtspin, Cityspin;
    Button signup, Emaill, Phone;
    CountryCodePicker Cpp;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth FAuth;
    String role = "DeliveryPerson";
    String city, cityy, district, fname, lname, mobile, confirmpassword, password, house, emailid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_registeration);

        Fname = (TextInputLayout) findViewById(R.id.fname);
        Lname = (TextInputLayout) findViewById(R.id.lname);
        Pass = (TextInputLayout) findViewById(R.id.password);
        Email = (TextInputLayout) findViewById(R.id.Emailid);
        cfpass = (TextInputLayout) findViewById(R.id.confirmpassword);
        mobileno = (TextInputLayout) findViewById(R.id.mobileno);
        houseno = (TextInputLayout) findViewById(R.id.Houseno);
        area = (TextInputLayout) findViewById(R.id.Areaa);
        postcode = (TextInputLayout) findViewById(R.id.Postcodee);
        Districtspin = (Spinner) findViewById(R.id.District);
        Cityspin = (Spinner) findViewById(R.id.City);
        Emaill = (Button) findViewById(R.id.emaillid);
        signup = (Button) findViewById(R.id.Signupp);
        Phone = (Button) findViewById(R.id.Phonenumber);
        final ProgressDialog mDialog = new ProgressDialog(Delivery_registeration.this);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);


        Cityspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                city = value.toString().trim();
                if (city.equals("Ho Chi Minh")) {
                    ArrayList<String> list = new ArrayList<>();
                    for (String text : HoChiMinh) {
                        list.add(text);
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Delivery_registeration.this, android.R.layout.simple_spinner_item, list);
                    Cityspin.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Districtspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                district = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        databaseReference = firebaseDatabase.getInstance().getReference("DeliveryPerson");
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = Fname.getEditText().getText().toString().trim();
                lname = Lname.getEditText().getText().toString().trim();
                mobile = mobileno.getEditText().getText().toString().trim();
                emailid = Email.getEditText().getText().toString().trim();
                password = Pass.getEditText().getText().toString().trim();
                confirmpassword = cfpass.getEditText().getText().toString().trim();
                house = houseno.getEditText().getText().toString().trim();

                if (isValid()) {


                    mDialog.setMessage("Registering please wait...");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("User").child(useridd);
                                final HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String, String> hashMappp = new HashMap<>();
                                        hashMappp.put("City", cityy);
                                        hashMappp.put("ConfirmPassword", confirmpassword);
                                        hashMappp.put("EmailID", emailid);
                                        hashMappp.put("Fname", fname);
                                        hashMappp.put("House", house);
                                        hashMappp.put("Lname", lname);
                                        hashMappp.put("Mobile", mobile);
                                        hashMappp.put("Password", password);
                                        hashMappp.put("City", city);
                                        hashMappp.put("District", district);
                                        firebaseDatabase.getInstance().getReference("DeliveryPerson").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMappp).addOnCompleteListener(new OnCompleteListener<Void>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();

                                                FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_registeration.this);
                                                            builder.setMessage("Registered Successfully,Please Verify your Email");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();

                                                                    String phonenumber = Cpp.getSelectedCountryCodeWithPlus() + mobile;
                                                                    Intent b = new Intent(Delivery_registeration.this, Delivery_VerifyPhone.class);
                                                                    b.putExtra("phonenumber", phonenumber);
                                                                    startActivity(b);

                                                                }
                                                            });
                                                            AlertDialog alert = builder.create();
                                                            alert.show();

                                                        } else {
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.ShowAlert(Delivery_registeration.this, "Error", task.getException().getMessage());

                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });


                            } else {
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(Delivery_registeration.this, "Error", task.getException().getMessage());
                            }

                        }
                    });


                }
            }
        });

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent e = new Intent(Delivery_registeration.this, Delivery_LoginPhone.class);
                startActivity(e);
                finish();
            }
        });

        Emaill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Delivery_registeration.this, Delivery_Login.class);
                startActivity(a);
                finish();
            }
        });


    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        Fname.setErrorEnabled(false);
        Fname.setError("");
        Email.setErrorEnabled(false);
        Email.setError("");
        Lname.setErrorEnabled(false);
        Lname.setError("");
        Pass.setErrorEnabled(false);
        Pass.setError("");
        mobileno.setErrorEnabled(false);
        mobileno.setError("");
        cfpass.setErrorEnabled(false);
        cfpass.setError("");
        houseno.setErrorEnabled(false);
        houseno.setError("");

        boolean isValidname = false, isvalidpassword = false, isValidemail = false, isvalidconfirmpassword = false, isvalid = false, isvalidmobileno = false, isvalidlname = false, isvalidhousestreetno = false;
        if (TextUtils.isEmpty(fname)) {
            Fname.setErrorEnabled(true);
            Fname.setError("Firstname is required");
        } else {
            isValidname = true;
        }
        if (TextUtils.isEmpty(lname)) {
            Lname.setErrorEnabled(true);
            Lname.setError("Lastname is required");
        } else {
            isvalidlname = true;
        }
        if (TextUtils.isEmpty(emailid)) {
            Email.setErrorEnabled(true);
            Email.setError("Email is required");
        } else {
            if (emailid.matches(emailpattern)) {
                isValidemail = true;
            } else {
                Email.setErrorEnabled(true);
                Email.setError("Enter a valid Email Address");
            }

        }

        if (TextUtils.isEmpty(password)) {
            Pass.setErrorEnabled(true);
            Pass.setError("Password is required");
        } else {
            if (password.length() < 6) {
                Pass.setErrorEnabled(true);
                Pass.setError("password too weak");
            } else {
                isvalidpassword = true;
            }
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            cfpass.setErrorEnabled(true);
            cfpass.setError("Confirm Password is required");
        } else {
            if (!password.equals(confirmpassword)) {
                Pass.setErrorEnabled(true);
                Pass.setError("Password doesn't match");
            } else {
                isvalidconfirmpassword = true;
            }
        }
        if (TextUtils.isEmpty(mobile)) {
            mobileno.setErrorEnabled(true);
            mobileno.setError("Mobile number is required");
        } else {
            if (mobile.length() < 10) {
                mobileno.setErrorEnabled(true);
                mobileno.setError("Invalid mobile number");
            } else {
                isvalidmobileno = true;
            }
        }
        if (TextUtils.isEmpty(house)) {
            houseno.setErrorEnabled(true);
            houseno.setError("Field cannot be empty");
        } else {
            isvalidhousestreetno = true;
        }

        isvalid = (isValidname && isValidemail && isvalidlname && isvalidconfirmpassword && isvalidpassword && isvalidmobileno && isvalidhousestreetno) ? true : false;
        return isvalid;
    }
}
