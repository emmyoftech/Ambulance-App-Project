package com.example.ambulanceapp.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ambulanceapp.R;
import com.example.ambulanceapp.interfaces.PassedFunction;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DIalogue {
    View dialogueView;

    public DIalogue(View  view){
        dialogueView = view;
    }

    private void openDialogue (){
        LinearLayout dialogueBody = dialogueView.findViewById(R.id.dialogueContainer);
        dialogueBody.setVisibility(View.VISIBLE);
        dialogueBody.setClickable(true);
    }
    private void setCLickerFormessageDialogue(PassedFunction function){
        Button msgBtn = dialogueView.findViewById(R.id.messageDialogueBtn);
        msgBtn.setOnClickListener(v -> {
            function.run();
            closeDialogue();
        });
    }
    private void setCLickerFormessageDialogue(){
        Button msgBtn = dialogueView.findViewById(R.id.messageDialogueBtn);
        msgBtn.setOnClickListener(v -> closeDialogue());
    }
    private void closeDialogue (){
        LinearLayout dialogueBody = dialogueView.findViewById(R.id.dialogueContainer);
        LinearLayout questionDialogueBody = dialogueView.findViewById(R.id.messageDialogue);
        LinearLayout messageDialogueBody = dialogueView.findViewById(R.id.questionDialogue);

        dialogueBody.setVisibility(View.GONE);
        dialogueBody.setClickable(false);
        questionDialogueBody.setVisibility(View.GONE);
        messageDialogueBody.setVisibility(View.GONE);
    }
    private void setUpmessageDialodue (String stete, String msg){
        LinearLayout messageDIalogContainer = dialogueView.findViewById(R.id.messageDialogue);
        TextView title = dialogueView.findViewById(R.id.messageDialogueTitle),
                textBody = dialogueView.findViewById(R.id.messageDialogueTextBody);

        messageDIalogContainer.setVisibility(View.VISIBLE);

        switch (stete){
            case "warn":
                title.setTextColor(dialogueView.getResources().getColor(R.color.pal_yellow));
                title.setText(R.string.warn);
                break;
            case "success":
                title.setTextColor(dialogueView.getResources().getColor(R.color.pal_green));
                title.setText(R.string.succces);
                break;
            default:
                title.setTextColor(dialogueView.getResources().getColor(R.color.primary));
                title.setText(R.string.errorWord);
                break;
        }
        textBody.setText(msg);
    }

//    PUBLIC METHODS

    public void warn (String msg){
        openDialogue();
        setUpmessageDialodue("warn", msg);
        setCLickerFormessageDialogue();
    }
    public void warn (String msg, PassedFunction function){
        openDialogue();
        setUpmessageDialodue("warn", msg);
        setCLickerFormessageDialogue(function);
    }

    public void success (String msg){
        openDialogue();
        setUpmessageDialodue("success", msg);
        setCLickerFormessageDialogue();
    }
    public void success (String msg , PassedFunction function){
        openDialogue();
        setUpmessageDialodue("success", msg);
        setCLickerFormessageDialogue(function);
    }

    public void error (String msg){
        openDialogue();
        setUpmessageDialodue("error", msg);
        setCLickerFormessageDialogue();
    }
    public void error (String msg , PassedFunction function){
        openDialogue();
        setUpmessageDialodue("error", msg);
        setCLickerFormessageDialogue(function);
    }

    public void askquuestion (String msg, PassedFunction function){
        openDialogue();
        LinearLayout quesContaner = dialogueView.findViewById(R.id.questionDialogue);
        TextView quesTextBody = dialogueView.findViewById(R.id.quesTextBody);

        quesContaner.setVisibility(View.VISIBLE);
        quesTextBody.setText(msg);
        quesBtnSetup(function);
    }
    public void askquuestion (String msg, PassedFunction yesFunction, PassedFunction noFunction){
        openDialogue();
        LinearLayout quesContaner = dialogueView.findViewById(R.id.questionDialogue);
        TextView quesTextBody = dialogueView.findViewById(R.id.quesTextBody);

        quesContaner.setVisibility(View.VISIBLE);
        quesTextBody.setText(msg);
        quesBtnSetup(yesFunction, noFunction);
    }
    private void quesBtnSetup(PassedFunction yesFunction){
        Button yesBtn = dialogueView.findViewById(R.id.yes_btn);
        Button noBtn = dialogueView.findViewById((R.id.no_btn));
        yesBtn.setOnClickListener(v -> yesFunction.run());
        noBtn.setOnClickListener(v -> closeDialogue());
    }
    private void quesBtnSetup(PassedFunction yesFunction, PassedFunction noFunction){
        Button yesBtn = dialogueView.findViewById(R.id.yes_btn);
        Button noBtn = dialogueView.findViewById((R.id.no_btn));
        yesBtn.setOnClickListener(v -> yesFunction.run());
        noBtn.setOnClickListener(v -> {
            noFunction.run();
            closeDialogue();
        });
    }

    public void openLoader(){
        openDialogue();
        LinearLayout loader = dialogueView.findViewById(R.id.loader);
        loader.setVisibility(View.VISIBLE);
    }
    public void openLoader(String loadText){
        openDialogue();
        TextView loadtext = dialogueView.findViewById(R.id.load_text);
        LinearLayout loader = dialogueView.findViewById(R.id.loader);
        loadtext.setText(loadText);
        loader.setVisibility(View.VISIBLE);
    }

    public void openLoader(PassedFunction cancelFunction){
        openLoader();
        Button canBtn = dialogueView.findViewById(R.id.cancelBtn);
        ScheduledExecutorService executeService = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            canBtn.setVisibility(View.VISIBLE);
            canBtn.setOnClickListener(v -> cancelFunction.run());
        };
        executeService.schedule(task, 3, TimeUnit.SECONDS);
        executeService.shutdown();
    }

    public void closeLoader(){
        LinearLayout loader = dialogueView.findViewById(R.id.loader);
        Button canBtn = dialogueView.findViewById(R.id.cancelBtn);
        TextView loadtext = dialogueView.findViewById(R.id.load_text);
        loader.setVisibility(View.GONE);
        canBtn.setOnClickListener(v -> {});
        canBtn.setVisibility(View.GONE);
        loadtext.setText(dialogueView.getResources().getString(R.string.loading));
        closeDialogue();
    }
}
