package com.vgroup.c2cbuisness

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.vgroup.c2cembedcode.CallActivity
import com.vgroup.c2cembedcode.C2CConstants
import com.vgroup.c2cembedcode.pojo.InitiateC2C
import com.vgroup.c2cembedcode.pojo.Modes
import com.vgroup.c2cembedcode.pojo.ValidateOTP
import java.util.regex.Pattern


class Dashboard : AppCompatActivity() {

    var call_icon: ImageView? = null
    var msg_icon: ImageView? = null
    var email_icon: ImageView? = null
    var callActivity: CallActivity = CallActivity()
    var modes: Modes = Modes()
    val ALL_PERMISSIONS = 101
    val channelId = "642ab1fd23426f147b7af59d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_view)

        call_icon = findViewById(R.id.call_icon)
        msg_icon = findViewById(R.id.msg_icon)
        email_icon = findViewById(R.id.email_icon)

        getModes(channelId)

        call_icon!!.setOnClickListener {
            val permission = ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.RECORD_AUDIO
            )
            if (permission == PackageManager.PERMISSION_GRANTED) {
                getCallDetails(channelId, modes, C2CConstants.CALL)
            } else {
                showError("Message", "Allow permission from setting to make call")
            }
        }

        msg_icon!!.setOnClickListener {
            getCallDetails(channelId, modes, C2CConstants.SMS)
        }
        email_icon!!.setOnClickListener {
            getCallDetails(channelId, modes, C2CConstants.EMAIL)
        }

        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS)
    }

    private fun getCallDetails(channelId: String, modes: Modes, id: String) {
        if (modes.channel.preferences.isVerificationRequired) {
            val dialog = Dialog(this@Dashboard)
            dialog.setContentView(R.layout.call_popup)
            val window: Window = dialog.getWindow()!!
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            val countries = ArrayList<String>()
            for (country in modes.channel.countries) {
                countries.add(country.code + " " + country.country)
            }
            val countrySpinner = dialog.findViewById<Spinner>(R.id.country_spinner)
            val aa = ArrayAdapter<Any>(
                this, android.R.layout.simple_spinner_item,
                countries as List<Any>
            )
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            countrySpinner.adapter = aa
            var selectedCountry: String = ""
            countrySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    selectedCountry = ""
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedCountry = countries.get(position)
                }

            }


            val titleTextView = dialog.findViewById<TextView>(R.id.title_txt_view)
            val firstNameEdittxt = dialog.findViewById<EditText>(R.id.firstNameEditText)
            val lastNameEdittxt = dialog.findViewById<EditText>(R.id.lastNameEditText)
            val numberEdittxt = dialog.findViewById<EditText>(R.id.numberEditText)
            val mobileOTPEditText = dialog.findViewById<EditText>(R.id.mobileOTPEditText)
            val emailOTPEditText = dialog.findViewById<EditText>(R.id.emailOTPEditText)
            val msgEdittxt = dialog.findViewById<EditText>(R.id.messageEditText)
            val emailEditText = dialog.findViewById<EditText>(R.id.emailEditText)
            val mobileCodeButton = dialog.findViewById<MaterialButton>(R.id.get_code_button)
            val verifyEmailOtpButton =
                dialog.findViewById<MaterialButton>(R.id.verifyEmailOtpButton)
            val codeLayout = dialog.findViewById<LinearLayout>(R.id.get_code_layout)
            val emailVerifyLayout = dialog.findViewById<LinearLayout>(R.id.verify_email_layout)
            val nameLayout = dialog.findViewById<LinearLayout>(R.id.name_layout)
            val numberLayout = dialog.findViewById<LinearLayout>(R.id.number_layout)
            val mobileOTPLayout = dialog.findViewById<LinearLayout>(R.id.mobileOTPLayout)
            val emailLayout = dialog.findViewById<LinearLayout>(R.id.email_layout)
            val emailOTPLayout =
                dialog.findViewById<LinearLayout>(R.id.emailOTPLayout)
            val detailsLayout = dialog.findViewById<LinearLayout>(R.id.details_layout)
            val textInputLayout = dialog.findViewById<TextInputLayout>(R.id.messageLayout)
            val cancelTextView = dialog.findViewById<TextView>(R.id.cancelTextView)
            val termsCheckBox = dialog.findViewById<CheckBox>(R.id.accept_terms_and_conditions)
            val connectButton = dialog.findViewById<MaterialButton>(R.id.connectButton)
            val messageEditText = dialog.findViewById<EditText>(R.id.messageEditText)
            val termsTextView = dialog.findViewById<TextView>(R.id.Terms_and_condition_text)
            val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)
            titleTextView.setText(id)

            val ss = SpannableString("I agree to the terms and conditions")
            val clickableSpan: ClickableSpan = object : ClickableSpan() {

                override fun onClick(textView: View) {

//                    startActivity(Intent(this@MainActivity, signup_activity::class.java))
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.setColor(Color.BLUE)
                    ds.isUnderlineText = false
                }
            }
            ss.setSpan(clickableSpan, 15, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val boldSpan = StyleSpan(Typeface.BOLD)
            ss.setSpan(boldSpan, 15, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            termsTextView.text = ss
            termsTextView.movementMethod = LinkMovementMethod.getInstance()
            termsTextView.highlightColor = Color.TRANSPARENT

            connectButton.setOnClickListener {
                hideKeyboard(currentFocus ?: View(this))
                if (termsCheckBox.isChecked) {
                    if (TextUtils.isEmpty(firstNameEdittxt.text.toString())) {
                        showError("Message", "Enter first name.")
                    } else if (TextUtils.isEmpty(lastNameEdittxt.text.toString())) {
                        showError("Message", "Enter last name.")
                    } else if (TextUtils.isEmpty(numberEdittxt.text.toString())) {
                        showError("Message", "Enter valid contact number.")
                    } else if (TextUtils.isEmpty(emailEditText.text.toString())) {
                        showError("Message", "Please enter email address.")
                    } else if (!isValidString(emailEditText.text.toString())) {
                        showError("Message", "Please enter valid email address.")
                    } else if (TextUtils.isEmpty(messageEditText.text.toString())) {
                        showError("Message", "Please enter message here.")
                    } else {

                        var initiateC2C = InitiateC2C()
                        initiateC2C.channelId = channelId
                        initiateC2C.name =
                            firstNameEdittxt.text.toString() + " " + lastNameEdittxt.text.toString()

                        initiateC2C.numotp = mobileOTPEditText.text.toString()
                        initiateC2C.mailotp = emailOTPEditText.text.toString()

                        initiateC2C.number = numberEdittxt.text.toString()
                        for (country in modes.channel.countries) {
                            if ((country.code + " " + country.country).contentEquals(selectedCountry)) {
                                initiateC2C.countrycode = country.code
                                break
                            }
                        }

                        if (id.equals(C2CConstants.CALL)) {
                            initiateC2C.email = emailEditText.text.toString()
                            initiateC2C.message = messageEditText.text.toString()
                            initiateCall(initiateC2C, dialog,progressBar)
                        } else {

                            if (id.equals(C2CConstants.SMS)) {
                                initiateC2C.message = messageEditText.text.toString()
                                sendMessage(initiateC2C, dialog,progressBar)
                            } else if (id.equals(C2CConstants.EMAIL)) {
                                initiateC2C.email = messageEditText.text.toString()
                                sendEmail(initiateC2C, dialog,progressBar)
                            }
                        }

                    }
                } else {
                    showError("Message", "Please check and agree the terms & conditions.")
                }
            }

            cancelTextView.setOnClickListener {
                dialog.dismiss()
            }
//            android:drawableRight="@android:drawable/ic_delete"
            mobileCodeButton.setOnClickListener {
                if (numberEdittxt.text.toString().isEmpty()) {
                    showError("Message", "Enter valid contact number.")
                }
                else {
                    for (country in modes.channel.countries) {
                        if ((country.code + " " + country.country).contentEquals(selectedCountry)) {
                            getSMSOTP(
                                channelId,
                                country.code,
                                numberEdittxt.text.toString(),
                                mobileOTPLayout
                            )
                            break
                        }
                    }

                }
            }
            verifyEmailOtpButton.setOnClickListener {
                if (emailEditText.text.toString().isEmpty()) {
                    showError("Message", "Please enter valid email address.")
                } else {
                    getEmailOTP(channelId, emailEditText.text.toString(), emailOTPLayout)
                }

            }

            if (modes.channel.preferences.email && modes.channel.preferences.verifyemail) {
                mobileOTPEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s?.length == 4) {
                            var validateOTP = ValidateOTP()
                            validateOTP.channelId = channelId
                            validateOTP.number = numberEdittxt.text.toString()
                            validateOTP.otp = mobileOTPEditText.text.toString()
                            for (country in modes.channel.countries) {
                                if ((country.code + " " + country.country).contentEquals(
                                        selectedCountry
                                    )
                                ) {
                                    validateOTP.countrycode = country.code
                                    break
                                }
                            }

                            validateOTP(mobileOTPEditText, validateOTP)
                        }
                    }
                })
            }
            if (modes.channel.preferences.contact && modes.channel.preferences.verifycontact) {
                emailOTPEditText.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s?.length == 4) {
                            var validateOTP = ValidateOTP()
                            validateOTP.channelId = channelId
                            validateOTP.email = emailEditText.text.toString()
                            validateOTP.otp = emailOTPEditText.text.toString()

                            validateEmailOTP(emailOTPEditText, validateOTP)
                        }
                    }
                })
            }
            mobileOTPLayout.visibility = View.GONE
            emailOTPLayout.visibility = View.GONE
            nameLayout.visibility = if (modes.channel.preferences.name) View.VISIBLE else View.GONE
            numberLayout.visibility =
                if (modes.channel.preferences.contact) View.VISIBLE else View.GONE
            emailLayout.visibility =
                if (modes.channel.preferences.email) View.VISIBLE else View.GONE
            textInputLayout.visibility =
                if (modes.channel.preferences.message) View.VISIBLE else View.GONE
            codeLayout.visibility =
                if (modes.channel.preferences.verifycontact) View.VISIBLE else View.GONE
            emailVerifyLayout.visibility =
                if (modes.channel.preferences.verifyemail) View.VISIBLE else View.GONE
            dialog.setCancelable(false)
            dialog.show()
        }
        else {

            val dialog = Dialog(this@Dashboard)
            dialog.setContentView(R.layout.call_popup)
            val window: Window = dialog.getWindow()!!
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            val detailsLayout = dialog.findViewById<LinearLayout>(R.id.details_layout)
            val formLayout = dialog.findViewById<LinearLayout>(R.id.form_layout)
            if (id.equals(C2CConstants.CALL)) {
                detailsLayout.visibility = View.GONE
            } else {
                formLayout.visibility = View.GONE
            }
            val cancelTextView = dialog.findViewById<TextView>(R.id.cancelTextView)
            val termsCheckBox = dialog.findViewById<CheckBox>(R.id.accept_terms_and_conditions)
            val connectButton = dialog.findViewById<MaterialButton>(R.id.connectButton)
            val messageEditText = dialog.findViewById<EditText>(R.id.messageEditText)
            val titleTextView = dialog.findViewById<TextView>(R.id.title_txt_view)
            val termsTextView = dialog.findViewById<TextView>(R.id.Terms_and_condition_text)
            val progressBar = dialog.findViewById<ProgressBar>(R.id.progressBar)

            titleTextView.setText(id)

            val ss = SpannableString("I agree to the terms and conditions")
            val clickableSpan: ClickableSpan = object : ClickableSpan() {

                override fun onClick(textView: View) {

//                    startActivity(Intent(this@MainActivity, signup_activity::class.java))
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.setColor(Color.BLUE)
                    ds.isUnderlineText = false
                }
            }
            ss.setSpan(clickableSpan, 15, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            val boldSpan = StyleSpan(Typeface.BOLD)
            ss.setSpan(boldSpan, 15, 35, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            termsTextView.text = ss
            termsTextView.movementMethod = LinkMovementMethod.getInstance()
            termsTextView.highlightColor = Color.TRANSPARENT

            cancelTextView.setOnClickListener {
                dialog.dismiss()
            }

            connectButton.setOnClickListener {
                hideKeyboard(currentFocus ?: View(this))
                if (termsCheckBox.isChecked) {
                    var initiateC2C = InitiateC2C()
                    initiateC2C.channelId = channelId
                    if (id.equals(C2CConstants.CALL)) {
                        initiateCall(initiateC2C, dialog,progressBar)
                    } else {
                        if (TextUtils.isEmpty(messageEditText.text.toString())) {
                            showError("Message", "Please enter message")
                        } else if (id.equals(C2CConstants.SMS)) {
                            initiateC2C.message = messageEditText.text.toString()
                            sendMessage(initiateC2C, dialog,progressBar)
                        } else if (id.equals(C2CConstants.EMAIL)) {
                            initiateC2C.email = messageEditText.text.toString()
                            sendEmail(initiateC2C, dialog, progressBar)
                        }
                    }
                } else {
                    showError("Message", "Please check and agree the terms & conditions.")
                }
            }
            dialog.setCancelable(false)
            dialog.show()

        }

    }

    private fun validateEmailOTP(emailOTPEditText: EditText?, validateOTP: ValidateOTP) {}
//    {
//        val map: MutableMap<Any, Any> = HashMap()
//        val jsonString = Gson().toJson(validateOTP)
//        NetworkManager().verifyEmailOTP(
//            object : NetworkEventListener {
//                override fun OnSuccess(obj: Any) {
//                    var successC2C: SuccessC2C = obj as SuccessC2C
//                    emailOTPEditText?.setCompoundDrawablesWithIntrinsicBounds(
//                        0,
//                        0,
//                        com.vgroup.c2cembedcode.R.drawable.ic_call_black_24dp,
//                        0
//                    );
//                }
//
//                override fun OnError(exception: VolleyError) {
//                    VolleyErrorHandling.errorHandling(exception, this@Dashboard)
//                }
//            }, jsonString,"",""
//        )
//    }

    private fun validateOTP(mobileOTPEditText: EditText?, validateOTP: ValidateOTP){}
//    {
//        val map: MutableMap<Any, Any> = HashMap()
//        val jsonString = Gson().toJson(validateOTP)
//        NetworkManager().verifyMobileOTP(
//            object : NetworkEventListener {
//                override fun OnSuccess(obj: Any) {
//                    var successC2C: SuccessC2C = obj as SuccessC2C
//                    mobileOTPEditText?.setCompoundDrawablesWithIntrinsicBounds(
//                        0,
//                        0,
//                        com.vgroup.c2cembedcode.R.drawable.ic_call_black_24dp,
//                        0
//                    );
//                }
//
//                override fun OnError(exception: VolleyError) {
//                    VolleyErrorHandling.errorHandling(exception, this@Dashboard)
//                }
//            }, jsonString,"",""
//        )
//
//    }

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isValidString(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun sendEmail(initiateC2C: InitiateC2C, dialog: Dialog,progressBar: ProgressBar){}
//    {
//        progressBar.visibility = View.VISIBLE
//        val map: MutableMap<Any, Any> = HashMap()
//        val jsonString = Gson().toJson(initiateC2C)
//        NetworkManager().sendEmail(
//            object : NetworkEventListener {
//                override fun OnSuccess(obj: Any) {
//                    progressBar.visibility = View.GONE
//                    var successC2C: SuccessC2C = obj as SuccessC2C
//                    showError("Success", "Email sent successfully")
//
//                    dialog.dismiss()
//                }
//
//                override fun OnError(exception: VolleyError) {
//                    progressBar.visibility = View.GONE
//                    VolleyErrorHandling.errorHandling(exception, this@Dashboard)
//                }
//            }, jsonString,"",""
//        )
//
//    }

    private fun sendMessage(initiateC2C: InitiateC2C, dialog: Dialog,progressBar: ProgressBar){}
//    {
//        progressBar.visibility = View.VISIBLE
//        val map: MutableMap<Any, Any> = HashMap()
//        val jsonString = Gson().toJson(initiateC2C)
//        NetworkManager().sendSMS(
//            object : NetworkEventListener {
//                override fun OnSuccess(obj: Any) {
//                    progressBar.visibility = View.GONE
//                    var successC2C: SuccessC2C = obj as SuccessC2C
//                    showError("Success", "SMS sent successfully")
//                    dialog.dismiss()
//                }
//
//                override fun OnError(exception: VolleyError) {
//                    progressBar.visibility = View.GONE
//                    VolleyErrorHandling.errorHandling(exception, this@Dashboard)
//                }
//            }, jsonString,"","",""
//        )
//
//    }

    private fun getEmailOTP(channelID: String, emailID: String, emailOTPLayout: LinearLayout){}
//    {
//
//        val map: MutableMap<Any, Any> = HashMap()
//
//        NetworkManager().getOTPForEmail(
//            object : NetworkEventListener {
//                override fun OnSuccess(obj: Any) {
//                    var successC2C: SuccessC2C = obj as SuccessC2C
//                    emailOTPLayout.visibility = View.VISIBLE
//                    showError("Success", "verification code sent successfully.")
//                }
//
//                override fun OnError(exception: VolleyError) {
//                    VolleyErrorHandling.errorHandling(exception, this@Dashboard)
//                }
//            }, channelID, emailID,"",""
//        )
//
//    }

    private fun getSMSOTP(
        channelID: String,
        countryCode: String,
        number: String,
        mobileOTPLayout: LinearLayout
    ) {}
//    {
//        val map: MutableMap<Any, Any> = HashMap()
//
//        NetworkManager().getOTPForSMS(
//            object : NetworkEventListener {
//                override fun OnSuccess(obj: Any) {
//
//                    var successC2C: SuccessC2C = obj as SuccessC2C
//                    mobileOTPLayout.visibility = View.VISIBLE
//                    showError("Success", "verification code sent successfully.")
//                }
//
//                override fun OnError(exception: VolleyError) {
//                    VolleyErrorHandling.errorHandling(exception, this@Dashboard)
//                }
//            }, channelID, countryCode, number,"",""
//        )
//
//    }

    private fun showError(title: String, message: String) {
        var builder = AlertDialog.Builder(this)

        builder.setMessage(message)
            .setTitle(title)
            .setPositiveButton("Ok", DialogInterface.OnClickListener { dialogInterface, i ->
//                alert.dismiss()
                dialogInterface.dismiss();
            })
        val alert = builder.create()
        alert.show()


    }

    private fun initiateCall(initiateC2C: InitiateC2C, dialog: Dialog,progressBar: ProgressBar){}
//    {
//        progressBar.visibility = View.VISIBLE
//        val map: MutableMap<Any, Any> = HashMap()
//        val jsonString = Gson().toJson(initiateC2C)
//        NetworkManager().initiateCall(
//            object : NetworkEventListener {
//                override fun OnSuccess(obj: Any) {
//                    progressBar.visibility = View.GONE
//                    dialog.dismiss()
//
//                    var tokenPojo: TokenPojo = obj as TokenPojo
//                    callActivity.startCall(
//                        tokenPojo.verified.call,
//                        tokenPojo.token.value,
//                        this@Dashboard
//                    )
//                    val dialog = Dialog(this@Dashboard)
//                    dialog.setContentView(R.layout.call_menu)
//                    val chronometer = dialog.findViewById<Chronometer>(R.id.chronometer)
//                    val holdActionFab =
//                        dialog.findViewById<FloatingActionButton>(R.id.hold_action_fab)
//                    val muteActionFab =
//                        dialog.findViewById<FloatingActionButton>(R.id.mute_action_fab)
//                    val hangUpActionFab =
//                        dialog.findViewById<FloatingActionButton>(R.id.hangup_action_fab)
//                    holdActionFab.setOnClickListener {
//                        callActivity.hold(this@Dashboard, holdActionFab)
//                    }
//                    muteActionFab.setOnClickListener {
//                        callActivity.mute(this@Dashboard, muteActionFab)
//                    }
//                    hangUpActionFab.setOnClickListener {
//                        callActivity.disconnect()
//                        dialog.dismiss()
//                    }
//                    chronometer.base = SystemClock.elapsedRealtime()
//                    chronometer.start()
//                    dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent);
//                    dialog.setCancelable(false)
//                    dialog.show()
//                }
//
//                override fun OnError(exception: VolleyError) {
//                    progressBar.visibility = View.GONE
//                    VolleyErrorHandling.errorHandling(exception, this@Dashboard)
//                }
//            }, jsonString, this@Dashboard, callActivity,"",""
//        )
//    }

    private fun getModes(channelId: String) {
//        val map: MutableMap<Any, Any> = HashMap()
//
//        NetworkManager().getModes(object : NetworkEventListener {
//            override fun OnSuccess(channelModes: Any) {
//                modes = channelModes as Modes
//            }
//
//            override fun OnError(exception: VolleyError) {
//                VolleyErrorHandling.errorHandling(exception, this@Dashboard)
//            }
//        }, channelId, "",call_icon, msg_icon, email_icon)
    }
}
