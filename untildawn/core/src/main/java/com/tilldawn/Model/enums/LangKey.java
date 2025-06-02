package com.tilldawn.Model.enums;

import java.util.HashMap;

public enum LangKey {
    Back(new HashMap<Language, String>() {{
        put(Language.English, "Back");
        put(Language.French, "Dos");
    }}),

    LoginMenuTitle(new HashMap<Language, String>() {{
      put(Language.English, "20 MINUTES TILL DAWN");
      put(Language.French, "20 MINUTES AVANT L'AUBE");
    }}),
    LoginMenuLoginLabel(new HashMap<Language, String>() {{
        put(Language.English, "LOGIN");
        put(Language.French, "SE CONNECTER");
    }}),
    UsernameLabel(new HashMap<Language, String>() {{
        put(Language.English, "username");
        put(Language.French, "nom d'utilisateur");
    }}),
    PasswordLabel(new HashMap<Language, String>() {{
        put(Language.English, "password");
        put(Language.French, "mot de passe");
    }}),
    LoginMenuLoginButton(new HashMap<Language, String>() {{
        put(Language.English, "Login");
        put(Language.French, "Se connecter");
    }}),
    LoginMenuSignUpButton(new HashMap<Language, String>() {{
        put(Language.English, "Sign Up");
        put(Language.French, "S'inscrire");
    }}),

    EnglishLanguage(new HashMap<Language, String>() {{
        put(Language.English, "English");
        put(Language.French, "Anglaise");
    }}),
    FrenchLanguage(new HashMap<Language, String>() {{
        put(Language.English, "French");
        put(Language.French, "Française");
    }}),
    LoginMenuForgetPasswordButton(new HashMap<Language, String>() {{
        put(Language.English, "Forget Password");
        put(Language.French, "Mot de passe oublié");
    }}),
    LoginMenuSuccessfulConditionMessage(new HashMap<Language, String>() {{
        put(Language.English, "Logged in Successfully:)");
        put(Language.French, "Connecté avec succès:)");
    }}),
    LoginMenuFailedConditionMessage(new HashMap<Language, String>() {{
        put(Language.English, "Incorrect Username or Password!");
        put(Language.French, "Nom d'utilisateur ou mot de passe incorrect!");
    }}),




    SignUpMenuTitle(new HashMap<Language, String>() {{
        put(Language.English, "SIGN UP");
        put(Language.French, "S'INSCRIRE");
    }}),
    SignUpMenuPickQuestionLabel(new HashMap<Language, String>() {{
        put(Language.English, "Pick a security question");
        put(Language.French, "Choisissez une question de sécurité");
    }}),
    FavoriteColorQuestion(new HashMap<Language, String>() {{
        put(Language.English, "What is your favorite color?");
        put(Language.French, "Quelle est ta couleur préférée?");
    }}),
    PetNameQuestion(new HashMap<Language, String>() {{
        put(Language.English, "What is your pet's name?");
        put(Language.French, "Quel est le nom de votre animal de compagnie?");
    }}),
    MotherNameQuestion(new HashMap<Language, String>() {{
        put(Language.English, "What is your mother's maiden name?");
        put(Language.French, "Quel est le nom de jeune fille de ta mère?");
    }}),
    SchoolNameQuestion(new HashMap<Language, String>() {{
        put(Language.English, "What was the name of your first school?");
        put(Language.French, "Quel était le nom de votre première école?");
    }}),
    FavoriteMovieQuestion(new HashMap<Language, String>() {{
        put(Language.English, "What is your favorite movie?");
        put(Language.French, "Quel est ton film préféré?");
    }}),
    BornQuestion(new HashMap<Language, String>() {{
        put(Language.English, "Where were you born?");
        put(Language.French, "Où êtes-vous né?");
    }}),
    FriendNameQuestion(new HashMap<Language, String>() {{
        put(Language.English, "What is your best friend's name?");
        put(Language.French, "Comment s'appelle ton meilleur ami?");
    }}),
    SignUpMenuAnswerLabel(new HashMap<Language, String>() {{
        put(Language.English, "answer");
        put(Language.French, "répondre");
    }}),
    SignUpMenuSignUpButton(new HashMap<Language, String>() {{
        put(Language.English, "Sign Up");
        put(Language.French, "S'inscrire");
    }}),
    SignUpMenuGuestButton(new HashMap<Language, String>() {{
        put(Language.English, "Play as a Guest");
        put(Language.French, "Jouez en tant qu'invité");
    }}),
    UsernameTaken(new HashMap<Language, String>() {{
        put(Language.English, "Username is already taken.");
        put(Language.French, "Le nom d'utilisateur est déjà pris.");
    }}),
    SignUpMenuAnswerSecurityQuestion(new HashMap<Language, String>() {{
        put(Language.English, "Please answer the security question");
        put(Language.French, "Veuillez répondre à la question de sécurité");
    }}),
    SignUpMenuSuccessful(new HashMap<Language, String>() {{
        put(Language.English, "Signed up Successfully:)");
        put(Language.French, "Inscription réussie:)");
    }}),
    WeakPassword(new HashMap<Language, String>() {{
        put(Language.English, "Password is too weak!");
        put(Language.French, "Le mot de passe est trop faible!");
    }}),
    GuestUsername(new HashMap<Language, String>() {{
        put(Language.English, "Guest");
        put(Language.French, "Invitée");
    }}),

    ForgetMenuTitle(new HashMap<Language, String>() {{
        put(Language.English, "Change Password");
        put(Language.French, "Changer le mot de passe");
    }}),

    ForgetMenuNewPasswordLabel(new HashMap<Language, String>() {{
        put(Language.English, "new password");
        put(Language.French, "nouveau mot de passe");
    }}),

    ForgetMenuContinueButton(new HashMap<Language, String>() {{
        put(Language.English, "continue");
        put(Language.French, "continuer");
    }}),

    ForgetMenuSubmitAnswerButton(new HashMap<Language, String>() {{
        put(Language.English, "submit answer");
        put(Language.French, "soumettre la réponse");
    }}),

    ForgetMenuInvalidUsername(new HashMap<Language, String>() {{
        put(Language.English, "Invalid Username");
        put(Language.French, "Nom d'utilisateur invalide");
    }}),

    ForgetMenuIncorrectAnswer(new HashMap<Language, String>() {{
        put(Language.English, "The answer is incorrect!");
        put(Language.French, "La réponse est incorrecte!");
    }}),

    SuccessfulChangePassword(new HashMap<Language, String>() {{
        put(Language.English, "Password changed successfully:)");
        put(Language.French, "Le mot de passe a été modifié avec succès:)");
    }}),



    MainMenuWelcome(new HashMap<Language, String>() {{
        put(Language.English, "WELCOME ");
        put(Language.French, "BIENVENUE ");
    }}),

    MainMenuScoreLabel(new HashMap<Language, String>() {{
        put(Language.English, "Score");
        put(Language.French, "Score");
    }}),

    SettingsLabel(new HashMap<Language, String>() {{
        put(Language.English, "Settings");
        put(Language.French, "Paramètres");
    }}),

    ProfileMenuLabel(new HashMap<Language, String>() {{
        put(Language.English, "Profile Menu");
        put(Language.French, "Menu Profil");
    }}),

    PreGameMenuLabel(new HashMap<Language, String>() {{
        put(Language.English, "Pre Game Menu");
        put(Language.French, "Menu d'avant-match");
    }}),

    MainMenuContinueGameButton(new HashMap<Language, String>() {{
        put(Language.English, "Continue Game");
        put(Language.French, "Continuer le jeu");
    }}),

    ScoreboardLabel(new HashMap<Language, String>() {{
        put(Language.English, "Scoreboard");
        put(Language.French, "Tableau de bord");
    }}),

    TalentMenuLabel(new HashMap<Language, String>() {{
        put(Language.English, "Talent Menu");
        put(Language.French, "Menu des talents");
    }}),
    MainMenuLogoutButton(new HashMap<Language, String>() {{
        put(Language.English, "Logout");
        put(Language.French, "Déconnexion");
    }}),

    MainMenuNoSaveGameMessage(new HashMap<Language, String>() {{
        put(Language.English, "You do not have a saved game");
        put(Language.French, "Vous n'avez pas de partie sauvegardée");
    }}),


    ProfileMenuChangeUsernameLabel(new HashMap<Language, String>() {{
        put(Language.English, "Change Username");
        put(Language.French, "Changer le nom d'utilisateur");
    }}),

    ProfileMenuChangePasswordLabel(new HashMap<Language, String>() {{
        put(Language.English, "Change Password");
        put(Language.French, "Changer le mot de passe");
    }}),

    ProfileMenuChangeAvatarButton(new HashMap<Language, String>() {{
        put(Language.English, "Change Avatar");
        put(Language.French, "Changer d'avatar");
    }}),

    ProfileMenuNewUsernameLabel(new HashMap<Language, String>() {{
        put(Language.English, "new username");
        put(Language.French, "nouveau nom d'utilisateur");
    }}),

    ProfileMenuNewPasswordLabel(new HashMap<Language, String>() {{
        put(Language.English, "new password");
        put(Language.French, "nouveau mot de passe");
    }}),

    ProfileMenuSelectButton(new HashMap<Language, String>() {{
        put(Language.English, "Select");
        put(Language.French, "Sélectionner");
    }}),

    ProfileMenuDeleteAccountButton(new HashMap<Language, String>() {{
        put(Language.English, "Delete Account");
        put(Language.French, "Supprimer le compte");
    }}),

    ProfileMenuOKButton(new HashMap<Language, String>() {{
        put(Language.English, "OK");
        put(Language.French, "OK");
    }}),

    ProfileMenuDeleteAccountLabel(new HashMap<Language, String>() {{
        put(Language.English, "Do you want to delete your account?");
        put(Language.French, "Voulez-vous supprimer votre compte?");
    }}),

    ProfileMenuYes(new HashMap<Language, String>() {{
        put(Language.English, "Yes");
        put(Language.French, "Oui");
    }}),

    ProfileMenuNo(new HashMap<Language, String>() {{
        put(Language.English, "No");
        put(Language.French, "Non");
    }}),

    ProfileMenuGuestMessage(new HashMap<Language, String>() {{
        put(Language.English, "You are a guest");
        put(Language.French, "Vous êtes un invité");
    }}),

    ProfileMenuSuccessfulChangeUsernameMessage(new HashMap<Language, String>() {{
        put(Language.English, "Username Changed Successfully:)");
        put(Language.French, "Nom d'utilisateur modifié avec succès:)");
    }}),

    ProfileMenuSuccessfulChangeAvatarMessage(new HashMap<Language, String>() {{
        put(Language.English, "Avatar Changed Successfully:)");
        put(Language.French, "Avatar modifié avec succès:)");
    }}),

    ProfileMenuSelectAvatarMessage(new HashMap<Language, String>() {{
        put(Language.English, "Please select an avatar!");
        put(Language.French, "Veuillez sélectionner un avatar!");
    }}),
    GameChooseAbilityLabel(new HashMap<Language, String>() {{
        put(Language.English, "Choose an Ability");
        put(Language.French, "Choisissez une capacité");
    }}),

    GameChooseButton(new HashMap<Language, String>() {{
        put(Language.English, "Choose");
        put(Language.French, "Choisir");
    }}),

    GameResumeButton(new HashMap<Language, String>() {{
        put(Language.English, "Resume");
        put(Language.French, "Reprendre");
    }}),

    GameGiveUpButton(new HashMap<Language, String>() {{
        put(Language.English, "Give Up!");
        put(Language.French, "Abandonner!");
    }}),

    GameSaveAndExitButton(new HashMap<Language, String>() {{
        put(Language.English, "Save and Exit");
        put(Language.French, "Enregistrer et quitter");
    }}),

    CheatCodesLabel(new HashMap<Language, String>() {{
        put(Language.English, "Cheat Codes");
        put(Language.French, "Codes de triche");
    }}),

    GameGainedAbilitiesLabel(new HashMap<Language, String>() {{
        put(Language.English, "Gained Abilities");
        put(Language.French, "Capacités acquises");
    }}),

    GameKillLabel(new HashMap<Language, String>() {{
        put(Language.English, "Kill");
        put(Language.French, "Tuer");
    }}),

    GameLevelLabel(new HashMap<Language, String>() {{
        put(Language.English, "Level");
        put(Language.French, "Niveau");
    }}),

    GameSuccessfulSaveMessage(new HashMap<Language, String>() {{
        put(Language.English, "Game Saved Successfully");
        put(Language.French, "Jeu sauvegardé avec succès");
    }}),

    EndGameGameOver(new HashMap<Language, String>() {{
        put(Language.English, "GAME OVER!");
        put(Language.French, "JEU TERMINÉ!");
    }}),

    EndGameSurvived(new HashMap<Language, String>() {{
        put(Language.English, "YOU SURVIVED:)");
        put(Language.French, "TU AS SURVECU:)");
    }}),

    EndGameUsernameLabel(new HashMap<Language, String>() {{
        put(Language.English, "Username");
        put(Language.French, "Nom d'utilisateur");
    }}),

    EndGameTimeLabel(new HashMap<Language, String>() {{
        put(Language.English, "Survival Time");
        put(Language.French, "Temps de survie");
    }}),

    EndGameQuitButton(new HashMap<Language, String>() {{
        put(Language.English, "Quit to Menu");
        put(Language.French, "Quitter au menu");
    }}),

    ScoreboardTimeLabel(new HashMap<Language, String>() {{
        put(Language.English, "Max Survival Time");
        put(Language.French, "Temps de survie maximum");
    }}),

    ScoreboardYou(new HashMap<Language, String>() {{
        put(Language.English, "You");
        put(Language.French, "Toi");
    }}),

    SettingsMusicVolumeLabel(new HashMap<Language, String>() {{
        put(Language.English, "Music Volume");
        put(Language.French, "Volume de la musique");
    }}),

    SettingsMusicLabel(new HashMap<Language, String>() {{
        put(Language.English, "Music");
        put(Language.French, "Musique");
    }}),

    SettingsAutoReloadLabel(new HashMap<Language, String>() {{
        put(Language.English, "Auto-Reload");
        put(Language.French, "Rechargement automatique");
    }}),

    SettingsChangeControllersButton(new HashMap<Language, String>() {{
        put(Language.English, "Change KeyBoard Controllers");
        put(Language.French, "Changer les contrôleurs du clavier");
    }}),

    SettingsChangeButton(new HashMap<Language, String>() {{
        put(Language.English, "Change");
        put(Language.French, "Changement");
    }}),

    SettingsInvalidMessage(new HashMap<Language, String>() {{
        put(Language.English, "Invalid or Already Taken!");
        put(Language.French, "Invalide ou déjà pris!");
    }}),

    SettingsChangedMessage(new HashMap<Language, String>() {{
        put(Language.English, "Changed");
        put(Language.French, "Modifié");
    }}),


    ;

    private final HashMap<Language, String> translation;

    LangKey(HashMap<Language, String> translation) {
        this.translation = translation;
    }

    public String getTranslation(Language language) {
        return translation.get(language);
    }
}
