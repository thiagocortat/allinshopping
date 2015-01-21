package com.projetandoo.allinshopping.events;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ChangeRadioButtonEventListener
        implements OnCheckedChangeListener {

    @Override
    public void onCheckedChanged(RadioGroup radiogroup, int i) {
        RadioButton radiobutton = (RadioButton) radiogroup.findViewById(i);
        for (int k = 0, j = radiogroup.getChildCount(); k < j; k++) {
            RadioButton other = (RadioButton) radiogroup.getChildAt(k);
            if (!other.equals(radiobutton) && other.isChecked()) {
                other.setChecked(false);
            }
        }
    }
}
