package com.example.model

/**
 * Builds comprehensive, multi-topic learning units and interactive exercises
 * spanning everyday life beyond simple greetings:
 * - Food & Dining Out (Cafés, Restaurants, Street Food, Markets)
 * - Travel & City Transit (Taxis, Directions, Trains, Airports)
 * - Numbers, Time & Market Bargaining (Shopping, Currency, Hours)
 * - Social Life, Family & Hobbies (Friends, Sports, Weekends)
 * - Health, Pharmacy & Emergencies (Doctor, Medicine, Urgent Help)
 */
object CurriculumBuilder {

    fun buildUnitsForLanguage(lang: LanguageOption): List<LearningUnit> {
        val code = lang.code.uppercase()
        return when {
            code == "FR" -> buildFrenchUnits(lang)
            code == "ES" || code == "ES_MX" -> buildSpanishUnits(lang)
            code == "YO" -> buildYorubaUnits(lang)
            code == "IG" -> buildIgboUnits(lang)
            code == "HA" -> buildHausaUnits(lang)
            code == "PCM" -> buildPidginUnits(lang)
            code == "DE" -> buildGermanUnits(lang)
            code == "JA" -> buildJapaneseUnits(lang)
            else -> buildUniversalUnits(lang)
        }
    }

    // --------------------------------------------------------------------------
    // 1. FRENCH UNITS
    // --------------------------------------------------------------------------
    private fun buildFrenchUnits(lang: LanguageOption): List<LearningUnit> {
        return listOf(
            // Unit 1: Greetings & Foundations
            LearningUnit(
                id = "fr_unit_1",
                unitNumber = 1,
                title = "Greetings & Polite French",
                subtitle = "Master essential greetings, honorifics, and everyday courtesies",
                emoji = "🤝",
                themeColorHex = 0xFF58CC02, // Vibrant Duolingo Green
                progressPercent = 75,
                nodes = createNodesForUnit("fr_unit_1", listOf("Bonjour & Saluts", "Polite Words", "First Chat", "Grammar Quiz", "Treasure Chest"), activeIndex = 2),
                exercises = listOf(
                    InteractiveExercise(
                        id = "fr_1_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Assemble the polite French greeting in order:",
                        englishText = "Good morning, how are you today?",
                        targetText = "Bonjour comment allez-vous aujourd'hui ?",
                        pronunciation = "[bɔ̃.ʒuʁ kɔ.mɑ̃ a.le vu o.ʒuʁ.dɥi]",
                        jumbledWords = listOf("aujourd'hui ?", "Bonjour", "allez-vous", "comment"),
                        correctWordSequence = listOf("Bonjour", "comment", "allez-vous", "aujourd'hui ?"),
                        explanation = "'Comment allez-vous ?' is the standard polite way to ask 'How are you?' in French.",
                        cultureTip = "In France, always say 'Bonjour' first when entering any shop or café!"
                    ),
                    InteractiveExercise(
                        id = "fr_1_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Tap matching pairs to connect French words with English:",
                        pairItems = listOf(
                            PairMatchItem("1", "Bonjour", "Good morning"),
                            PairMatchItem("2", "S'il vous plaît", "Please"),
                            PairMatchItem("3", "Merci beaucoup", "Thank you"),
                            PairMatchItem("4", "Au revoir", "Goodbye")
                        ),
                        explanation = "Matching polite everyday phrases reinforces instant recall."
                    ),
                    InteractiveExercise(
                        id = "fr_1_listen",
                        type = ExerciseType.LISTENING,
                        prompt = "Listen closely and select what Camille says:",
                        targetText = "Bonsoir tout le monde !",
                        pronunciation = "[bɔ̃.swaʁ tu lə mɔ̃d]",
                        englishText = "Good evening everyone!",
                        options = listOf("Bonjour tout le monde !", "Bonsoir tout le monde !", "Bonne nuit les amis !"),
                        correctOptionIndex = 1,
                        explanation = "'Bonsoir' is strictly used in the evening; 'Bonne nuit' is reserved for bedtime."
                    ),
                    InteractiveExercise(
                        id = "fr_1_speaking",
                        type = ExerciseType.SPEAKING_ECHO,
                        prompt = "Listen to Camille, then hold the mic to practice speaking:",
                        targetText = "Enchanté de faire votre connaissance !",
                        pronunciation = "[ɑ̃.ʃɑ̃.te də fɛʁ vɔtʁ kɔ.nɛ.sɑ̃s]",
                        englishText = "Delighted to make your acquaintance!",
                        explanation = "'Enchanté' conveys polite warmth upon meeting someone for the first time."
                    ),
                    InteractiveExercise(
                        id = "fr_1_blank",
                        type = ExerciseType.FILL_IN_BLANK,
                        prompt = "Select the missing polite word to complete the sentence:",
                        blankSentence = "Un café, ___ vous plaît.",
                        options = listOf("s'il", "merci", "avec", "donne"),
                        correctOptionIndex = 0,
                        explanation = "'S'il vous plaît' (literally 'if it pleases you') is mandatory when asking for items in France."
                    )
                ),
                guidebookEntries = listOf(
                    GuidebookEntry(
                        "French Politeness Codes",
                        "Never forget the 'Magic Words' (Bonjour, S'il vous plaît, Merci, Au revoir). Failing to say 'Bonjour' can be interpreted as rude.",
                        listOf(
                            "Bonjour" to "Good morning / Hello",
                            "S'il vous plaît" to "Please",
                            "Merci beaucoup" to "Thank you very much",
                            "Enchanté" to "Pleased to meet you"
                        )
                    )
                ),
                reviewCards = listOf(
                    ReviewCard("Bonjour", "Good morning", "[bɔ̃.ʒuʁ]", "Bonjour madame !", "Good morning ma'am!", "Greetings"),
                    ReviewCard("S'il vous plaît", "Please", "[sil vu plɛ]", "Un café, s'il vous plaît.", "A coffee, please.", "Greetings"),
                    ReviewCard("Merci", "Thank you", "[mɛʁ.si]", "Merci beaucoup !", "Thank you very much!", "Greetings")
                )
            ),

            // Unit 2: Food, Cafés & Market Dining
            LearningUnit(
                id = "fr_unit_2",
                unitNumber = 2,
                title = "Bistro, Food & Café Dining",
                subtitle = "Order croissants, hot coffee, traditional dishes, and request the check",
                emoji = "🥐",
                themeColorHex = 0xFFFF9600, // Duolingo Warm Orange
                progressPercent = 40,
                nodes = createNodesForUnit("fr_unit_2", listOf("At the Café", "Ordering Food", "Wine & Drinks", "The Bill", "Chef's Chest"), activeIndex = 1),
                exercises = listOf(
                    InteractiveExercise(
                        id = "fr_2_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Construct this sentence to order politely at a Parisian bistro:",
                        englishText = "I would like a croissant and a café au lait, please.",
                        targetText = "Je voudrais un croissant et un café au lait s'il vous plaît",
                        pronunciation = "[ʒə vu.dʁɛ œ̃ kʁwa.sɑ̃ e œ̃ ka.fe o lɛ sil vu plɛ]",
                        jumbledWords = listOf("s'il vous plaît", "un café au lait", "Je voudrais", "un croissant", "et"),
                        correctWordSequence = listOf("Je voudrais", "un croissant", "et", "un café au lait", "s'il vous plaît"),
                        explanation = "Always use conditional 'Je voudrais' (I would like) rather than abrupt 'Je veux' (I want).",
                        cultureTip = "At Parisian cafés, waiters appreciate polite phrasing and patience."
                    ),
                    InteractiveExercise(
                        id = "fr_2_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match the culinary terms with their English meanings:",
                        pairItems = listOf(
                            PairMatchItem("1", "L'addition", "The bill / check"),
                            PairMatchItem("2", "De l'eau", "Water"),
                            PairMatchItem("3", "Le plat du jour", "Dish of the day"),
                            PairMatchItem("4", "Le serveur", "The waiter")
                        ),
                        explanation = "Mastering dining vocabulary makes eating out in Paris stress-free."
                    ),
                    InteractiveExercise(
                        id = "fr_2_listen",
                        type = ExerciseType.LISTENING,
                        prompt = "Listen to the waiter asking what you would like:",
                        targetText = "Qu'est-ce que je vous sers aujourd'hui ?",
                        pronunciation = "[kɛs kə ʒə vu sɛʁ o.ʒuʁ.dɥi]",
                        englishText = "What can I get for you today?",
                        options = listOf(
                            "Qu'est-ce que je vous sers aujourd'hui ?",
                            "Combien coûte le croissant chaud ?",
                            "Voulez-vous payer par carte ?"
                        ),
                        correctOptionIndex = 0,
                        explanation = "Standard formula used by waiters across France when greeting a table."
                    ),
                    InteractiveExercise(
                        id = "fr_2_speaking",
                        type = ExerciseType.SPEAKING_ECHO,
                        prompt = "Practice asking for the check like a local Parisian:",
                        targetText = "L'addition, s'il vous plaît !",
                        pronunciation = "[la.di.sjɔ̃ sil vu plɛ]",
                        englishText = "The check, please!",
                        explanation = "Waiters in France do not bring the bill until you explicitly ask for it."
                    ),
                    InteractiveExercise(
                        id = "fr_2_blank",
                        type = ExerciseType.FILL_IN_BLANK,
                        prompt = "Complete the sentence to ask for a table for two:",
                        blankSentence = "Une table pour ___ personnes, s'il vous plaît.",
                        options = listOf("deux", "l'addition", "croissant", "merci"),
                        correctOptionIndex = 0,
                        explanation = "'Deux' means two. 'Une table pour deux' is the standard reservation greeting."
                    )
                ),
                guidebookEntries = listOf(
                    GuidebookEntry(
                        "Café & Dining Etiquette",
                        "In France, bread and tap water ('une carafe d'eau') are complimentary with meals. Tipping ('pourboire') is already included in service, but rounding up a euro or two is welcomed.",
                        listOf(
                            "Je voudrais..." to "I would like...",
                            "Une carafe d'eau" to "A pitcher of tap water (free)",
                            "L'addition, s'il vous plaît" to "The check, please",
                            "C'est délicieux !" to "It is delicious!"
                        )
                    )
                ),
                reviewCards = listOf(
                    ReviewCard("Je voudrais", "I would like", "[ʒə vu.dʁɛ]", "Je voudrais une salade.", "I would like a salad.", "Dining Out"),
                    ReviewCard("L'addition", "The check", "[la.di.sjɔ̃]", "L'addition, s'il vous plaît.", "The check, please.", "Dining Out"),
                    ReviewCard("C'est délicieux", "It is delicious", "[sɛ de.li.sjø]", "Ce repas est délicieux !", "This meal is delicious!", "Dining Out")
                )
            ),

            // Unit 3: Travel, Directions & City Transit
            LearningUnit(
                id = "fr_unit_3",
                unitNumber = 3,
                title = "Travel, Transit & Directions",
                subtitle = "Navigate the Paris Metro, ask for directions, and take taxis",
                emoji = "🗺️",
                themeColorHex = 0xFF1CB0F6, // Duolingo Cyan Blue
                progressPercent = 15,
                nodes = createNodesForUnit("fr_unit_3", listOf("Metro Station", "Asking Where", "Taking a Taxi", "City Landmarks", "Travel Chest"), activeIndex = 0),
                exercises = listOf(
                    InteractiveExercise(
                        id = "fr_3_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Build the phrase to ask where the nearest metro station is:",
                        englishText = "Excuse me, where is the metro station?",
                        targetText = "Excusez-moi où se trouve la station de métro ?",
                        pronunciation = "[ɛk.sky.ze mwa u sə tʁuv la sta.sjɔ̃ də me.tʁo]",
                        jumbledWords = listOf("la station", "Excusez-moi", "où se trouve", "de métro ?"),
                        correctWordSequence = listOf("Excusez-moi", "où se trouve", "la station", "de métro ?"),
                        explanation = "'Où se trouve...' means 'Where is located...' and is very polite when asking strangers.",
                        cultureTip = "Always preface your direction question with 'Pardon' or 'Excusez-moi'."
                    ),
                    InteractiveExercise(
                        id = "fr_3_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match essential direction words with their meanings:",
                        pairItems = listOf(
                            PairMatchItem("1", "À gauche", "To the left"),
                            PairMatchItem("2", "À droite", "To the right"),
                            PairMatchItem("3", "Tout droit", "Straight ahead"),
                            PairMatchItem("4", "La gare", "The train station")
                        ),
                        explanation = "Directional words help you understand navigation answers instantly."
                    ),
                    InteractiveExercise(
                        id = "fr_3_listen",
                        type = ExerciseType.LISTENING,
                        prompt = "Listen to the driver giving directions:",
                        targetText = "Continuez tout droit puis tournez à droite.",
                        pronunciation = "[kɔ̃.ti.nɥe tu dʁwa pɥi tuʁ.ne a dʁwat]",
                        englishText = "Continue straight ahead then turn right.",
                        options = listOf(
                            "Continuez tout droit puis tournez à droite.",
                            "Prenez le métro direction Châtelet.",
                            "La gare est fermée aujourd'hui."
                        ),
                        correctOptionIndex = 0,
                        explanation = "'Tout droit' is straight ahead, while 'à droite' is to the right."
                    ),
                    InteractiveExercise(
                        id = "fr_3_speaking",
                        type = ExerciseType.SPEAKING_ECHO,
                        prompt = "Practice asking a taxi driver to take you to the airport:",
                        targetText = "À l'aéroport Charles de Gaulle, s'il vous plaît.",
                        pronunciation = "[a la.e.ʁɔ.pɔʁ ʃaʁl də ɡol sil vu plɛ]",
                        englishText = "To Charles de Gaulle airport, please.",
                        explanation = "Simply state your destination followed by 's'il vous plaît' to your driver."
                    )
                ),
                guidebookEntries = listOf(
                    GuidebookEntry(
                        "Navigating Paris Transit",
                        "The Paris Metro runs until about 1:15 AM on weekdays and 2:15 AM on weekends. Keep your validated ticket until you exit.",
                        listOf(
                            "Où est... ?" to "Where is...?",
                            "Un billet, s'il vous plaît" to "A ticket, please",
                            "À gauche / À droite" to "Left / Right",
                            "Tout droit" to "Straight ahead"
                        )
                    )
                )
            ),

            // Unit 4: Numbers, Time & Market Bargaining
            LearningUnit(
                id = "fr_unit_4",
                unitNumber = 4,
                title = "Numbers, Time & Shopping",
                subtitle = "Master counting 1-100, telling time, and shopping at flea markets",
                emoji = "🔢",
                themeColorHex = 0xFFCE82FF, // Duolingo Purple
                progressPercent = 0,
                nodes = createNodesForUnit("fr_unit_4", listOf("Counting 1-20", "Prices & Euros", "What Time Is It?", "Flea Market", "Gold Chest"), activeIndex = 0),
                exercises = listOf(
                    InteractiveExercise(
                        id = "fr_4_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Form the question to ask how much an item costs:",
                        englishText = "How much does this cost, please?",
                        targetText = "Combien ça coûte s'il vous plaît ?",
                        pronunciation = "[kɔ̃.bjɛ̃ sa kut sil vu plɛ]",
                        jumbledWords = listOf("s'il vous plaît ?", "ça coûte", "Combien"),
                        correctWordSequence = listOf("Combien", "ça coûte", "s'il vous plaît ?"),
                        explanation = "'Combien ça coûte ?' is the universal shopping question."
                    ),
                    InteractiveExercise(
                        id = "fr_4_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match the numbers and time words:",
                        pairItems = listOf(
                            PairMatchItem("1", "Dix", "Ten (10)"),
                            PairMatchItem("2", "Vingt", "Twenty (20)"),
                            PairMatchItem("3", "Cinquante", "Fifty (50)"),
                            PairMatchItem("4", "Quelle heure est-il ?", "What time is it?")
                        ),
                        explanation = "Numbers are crucial for prices, train platforms, and hours."
                    ),
                    InteractiveExercise(
                        id = "fr_4_blank",
                        type = ExerciseType.FILL_IN_BLANK,
                        prompt = "Complete: It costs fifteen euros.",
                        blankSentence = "Ça fait ___ euros, monsieur.",
                        options = listOf("quinze", "merci", "heure", "demain"),
                        correctOptionIndex = 0,
                        explanation = "'Quinze' means fifteen. 'Ça fait...' is commonly used for totals."
                    )
                )
            ),

            // Unit 5: Social Life, Family & Hobbies
            LearningUnit(
                id = "fr_unit_5",
                unitNumber = 5,
                title = "Social Life, Family & Hobbies",
                subtitle = "Talk about friends, family, weekend pastimes, and what you love",
                emoji = "⚽",
                themeColorHex = 0xFFFF4B4B, // Duolingo Coral Red
                progressPercent = 0,
                nodes = createNodesForUnit("fr_unit_5", listOf("Family Members", "My Hobbies", "Weekend Plans", "Making Friends", "Social Chest"), activeIndex = 0),
                exercises = listOf(
                    InteractiveExercise(
                        id = "fr_5_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Say that you love listening to French music with friends:",
                        englishText = "I love listening to music with my friends.",
                        targetText = "J'adore écouter de la musique avec mes amis",
                        pronunciation = "[ʒa.dɔʁ e.ku.te də la my.zik a.vɛk me.z‿a.mi]",
                        jumbledWords = listOf("avec mes amis", "de la musique", "J'adore écouter"),
                        correctWordSequence = listOf("J'adore écouter", "de la musique", "avec mes amis"),
                        explanation = "'J'adore' expresses strong enthusiasm for hobbies."
                    ),
                    InteractiveExercise(
                        id = "fr_5_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match family and social terms:",
                        pairItems = listOf(
                            PairMatchItem("1", "Mon ami", "My friend"),
                            PairMatchItem("2", "Ma famille", "My family"),
                            PairMatchItem("3", "Le week-end", "The weekend"),
                            PairMatchItem("4", "J'aime le football", "I like soccer")
                        ),
                        explanation = "Talking about hobbies helps build personal friendships."
                    )
                )
            ),

            // Unit 6: Emergencies, Pharmacy & Health
            LearningUnit(
                id = "fr_unit_6",
                unitNumber = 6,
                title = "Health, Pharmacy & Emergencies",
                subtitle = "Describe symptoms, find pharmacies, and ask for urgent assistance",
                emoji = "🏥",
                themeColorHex = 0xFF00CD9C, // Duolingo Teal Green
                progressPercent = 0,
                nodes = createNodesForUnit("fr_unit_6", listOf("At the Pharmacy", "Doctor Visit", "Emergency 112", "Urgent Care", "Hero Chest"), activeIndex = 0),
                exercises = listOf(
                    InteractiveExercise(
                        id = "fr_6_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Assemble the urgent plea for medical help:",
                        englishText = "Please help me, I need a doctor.",
                        targetText = "Aidez-moi s'il vous plaît j'ai besoin d'un médecin",
                        pronunciation = "[e.de mwa sil vu plɛ ʒe bə.zwɛ̃ dœ̃ med.sɛ̃]",
                        jumbledWords = listOf("j'ai besoin d'un médecin", "s'il vous plaît", "Aidez-moi"),
                        correctWordSequence = listOf("Aidez-moi", "s'il vous plaît", "j'ai besoin d'un médecin"),
                        explanation = "'Aidez-moi' is 'Help me' and 'J'ai besoin de' means 'I need'."
                    ),
                    InteractiveExercise(
                        id = "fr_6_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match health and emergency words:",
                        pairItems = listOf(
                            PairMatchItem("1", "La pharmacie", "The pharmacy"),
                            PairMatchItem("2", "J'ai mal à la tête", "I have a headache"),
                            PairMatchItem("3", "Le médecin", "The doctor"),
                            PairMatchItem("4", "Urgence", "Emergency")
                        ),
                        explanation = "Pharmacies in France are identifiable by a bright flashing green cross."
                    )
                )
            )
        )
    }

    // --------------------------------------------------------------------------
    // 2. YORUBA UNITS (Cultural Nigerian Course)
    // --------------------------------------------------------------------------
    private fun buildYorubaUnits(lang: LanguageOption): List<LearningUnit> {
        return listOf(
            LearningUnit(
                id = "yo_unit_1",
                unitNumber = 1,
                title = "Ìkíni & Yorùbá Respect Codes",
                subtitle = "Master greetings for elders, honorifics (Ẹ), and time of day",
                emoji = "🤝",
                themeColorHex = 0xFF58CC02,
                progressPercent = 70,
                nodes = createNodesForUnit("yo_unit_1", listOf("Ìkíni (Greetings)", "Ẹkú Àárọ̀", "Respect for Elders", "Review Quiz", "Treasure Chest"), activeIndex = 2),
                exercises = listOf(
                    InteractiveExercise(
                        id = "yo_1_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Construct the respectful morning greeting in Yorùbá:",
                        englishText = "Good morning, how did you wake up?",
                        targetText = "Ẹ káàárọ̀ o báwo ni ẹ ṣe jí ?",
                        jumbledWords = listOf("ẹ ṣe jí ?", "Ẹ káàárọ̀ o", "báwo ni"),
                        correctWordSequence = listOf("Ẹ káàárọ̀ o", "báwo ni", "ẹ ṣe jí ?"),
                        explanation = "'Ẹ káàárọ̀' uses the honorific prefix 'Ẹ' to show respect to elders or adults.",
                        cultureTip = "Yorùbá culture emphasizes respect (Ìtẹríba) - always greet with 'Ẹ' rather than 'O' when speaking with elders!"
                    ),
                    InteractiveExercise(
                        id = "yo_1_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Connect Yorùbá greetings with their English meanings:",
                        pairItems = listOf(
                            PairMatchItem("1", "Ẹ káàárọ̀", "Good morning"),
                            PairMatchItem("2", "Ẹ káàsán", "Good afternoon"),
                            PairMatchItem("3", "Ẹ ku alẹ", "Good evening"),
                            PairMatchItem("4", "Ẹ ṣeun gan", "Thank you very much")
                        )
                    )
                )
            ),
            LearningUnit(
                id = "yo_unit_2",
                unitNumber = 2,
                title = "Oúnjẹ, Buka & Lagos Dining",
                subtitle = "Order piping hot Jollof rice, tender beef, dodo, and pepper soup",
                emoji = "🍛",
                themeColorHex = 0xFFFF9600,
                progressPercent = 35,
                nodes = createNodesForUnit("yo_unit_2", listOf("At the Buka", "Jollof & Dodo", "Spicy Pepper Soup", "Paying the Mama", "Feast Chest"), activeIndex = 1),
                exercises = listOf(
                    InteractiveExercise(
                        id = "yo_2_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Build this phrase to order hot Jollof and fried plantain:",
                        englishText = "I want hot Jollof rice and plantain, please.",
                        targetText = "Mo fẹ́ ìrẹsì jọlọọfu àti dodo gbígbóná",
                        jumbledWords = listOf("àti dodo gbígbóná", "ìrẹsì jọlọọfu", "Mo fẹ́"),
                        correctWordSequence = listOf("Mo fẹ́", "ìrẹsì jọlọọfu", "àti dodo gbígbóná"),
                        explanation = "'Dodo' is fried ripe plantain, loved across Nigeria!"
                    ),
                    InteractiveExercise(
                        id = "yo_2_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match the authentic Nigerian food items:",
                        pairItems = listOf(
                            PairMatchItem("1", "Oúnjẹ", "Food / Meal"),
                            PairMatchItem("2", "Ẹran", "Meat / Beef"),
                            PairMatchItem("3", "Omi tútù", "Cold water"),
                            PairMatchItem("4", "Èló ni owó rẹ̀ ?", "How much is the cost?")
                        )
                    )
                )
            ),
            LearningUnit(
                id = "yo_unit_3",
                unitNumber = 3,
                title = "Ọjà Balógun & Bargaining",
                subtitle = "Negotiate prices respectfully, buy fabric, and count Naira",
                emoji = "🛍️",
                themeColorHex = 0xFFCE82FF,
                progressPercent = 0,
                nodes = createNodesForUnit("yo_unit_3", listOf("Entering Balogun", "Asking Prices", "Bargaining Polite", "Naira Counting", "Market Chest"), activeIndex = 0),
                exercises = listOf(
                    InteractiveExercise(
                        id = "yo_3_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Ask the trader politely to discount the price:",
                        englishText = "Please reduce the price a little bit.",
                        targetText = "Ẹ dín owó rẹ̀ kù díẹ̀ ẹ jọ̀wọ́",
                        jumbledWords = listOf("ẹ jọ̀wọ́", "Ẹ dín owó rẹ̀", "kù díẹ̀"),
                        correctWordSequence = listOf("Ẹ dín owó rẹ̀", "kù díẹ̀", "ẹ jọ̀wọ́"),
                        explanation = "Polite bargaining is an art form in Nigerian markets."
                    )
                )
            )
        )
    }

    // --------------------------------------------------------------------------
    // 3. IGBO UNITS
    // --------------------------------------------------------------------------
    private fun buildIgboUnits(lang: LanguageOption): List<LearningUnit> {
        return listOf(
            LearningUnit(
                id = "ig_unit_1",
                unitNumber = 1,
                title = "Nnọọ & Igbo Cultural Foundations",
                subtitle = "Master greetings, family titles, and everyday respect",
                emoji = "🤝",
                themeColorHex = 0xFF58CC02,
                progressPercent = 65,
                nodes = createNodesForUnit("ig_unit_1", listOf("Nnọọ (Welcome)", "Ututu Oma", "Respectful Tone", "Practice Quiz", "Treasure Chest"), activeIndex = 2),
                exercises = listOf(
                    InteractiveExercise(
                        id = "ig_1_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Arrange the Igbo morning greeting:",
                        englishText = "Good morning, how are you?",
                        targetText = "Ụtụtụ ọma kedu ka ị mere ?",
                        jumbledWords = listOf("kedu ka", "Ụtụtụ ọma", "ị mere ?"),
                        correctWordSequence = listOf("Ụtụtụ ọma", "kedu ka", "ị mere ?"),
                        explanation = "'Ụtụtụ ọma' means Good morning; 'Kedu ka ị mere ?' asks how you are doing."
                    ),
                    InteractiveExercise(
                        id = "ig_1_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match the essential Igbo expressions:",
                        pairItems = listOf(
                            PairMatchItem("1", "Nnọọ", "Welcome / Hello"),
                            PairMatchItem("2", "Dalụ", "Thank you"),
                            PairMatchItem("3", "Biko", "Please"),
                            PairMatchItem("4", "Ka ọ dị", "Goodbye / Until next time")
                        )
                    )
                )
            ),
            LearningUnit(
                id = "ig_unit_2",
                unitNumber = 2,
                title = "Nri & Onitsha Market Dining",
                subtitle = "Order delicious Ofe Nsala, pounded yam, fish, and Palm Wine",
                emoji = "🍲",
                themeColorHex = 0xFFFF9600,
                progressPercent = 20,
                nodes = createNodesForUnit("ig_unit_2", listOf("Mama's Kitchen", "Ofe Nsala", "Ji Akwụ", "Bill & Thanks", "Feast Chest"), activeIndex = 1),
                exercises = listOf(
                    InteractiveExercise(
                        id = "ig_2_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Order white soup and pounded yam politely:",
                        englishText = "I want white soup and pounded yam, please.",
                        targetText = "Achọrọ m ofe nsala na utara ji biko",
                        jumbledWords = listOf("na utara ji", "Achọrọ m ofe nsala", "biko"),
                        correctWordSequence = listOf("Achọrọ m ofe nsala", "na utara ji", "biko"),
                        explanation = "'Ofe Nsala' is the royal white soup cooked with fresh fish and aromatic herbs."
                    )
                )
            )
        )
    }

    // --------------------------------------------------------------------------
    // 4. HAUSA UNITS
    // --------------------------------------------------------------------------
    private fun buildHausaUnits(lang: LanguageOption): List<LearningUnit> {
        return listOf(
            LearningUnit(
                id = "ha_unit_1",
                unitNumber = 1,
                title = "Sannu & Hausa Courtesies",
                subtitle = "Master greetings, peaceful expressions (Lafiya), and respect",
                emoji = "🤝",
                themeColorHex = 0xFF58CC02,
                progressPercent = 60,
                nodes = createNodesForUnit("ha_unit_1", listOf("Sannu (Hello)", "Ina Kwana", "Lafiya Lau", "Quick Quiz", "Chest"), activeIndex = 2),
                exercises = listOf(
                    InteractiveExercise(
                        id = "ha_1_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Form the Hausa morning greeting and reply:",
                        englishText = "Good morning, how are you? - In peace / well.",
                        targetText = "Ina kwana ? Lafiya lau nagode",
                        jumbledWords = listOf("Lafiya lau nagode", "Ina kwana ?"),
                        correctWordSequence = listOf("Ina kwana ?", "Lafiya lau nagode"),
                        explanation = "'Ina kwana' asks how you spent the night, answered with 'Lafiya lau' (in peace and health)."
                    ),
                    InteractiveExercise(
                        id = "ha_1_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match Hausa words with their English equivalents:",
                        pairItems = listOf(
                            PairMatchItem("1", "Sannu", "Hello / Welcome"),
                            PairMatchItem("2", "Nagode", "Thank you"),
                            PairMatchItem("3", "Don Allah", "Please / For God's sake"),
                            PairMatchItem("4", "Sai an jima", "See you later")
                        )
                    )
                )
            ),
            LearningUnit(
                id = "ha_unit_2",
                unitNumber = 2,
                title = "Suya, Abinci & Kano Market",
                subtitle = "Order hot spiced Suya, Masa, Tuwo Shinkafa, and negotiate prices",
                emoji = "🥩",
                themeColorHex = 0xFFFF9600,
                progressPercent = 10,
                nodes = createNodesForUnit("ha_unit_2", listOf("Mai Suya Stand", "Tuwo & Miyan", "Kano Kurmi Market", "Bargaining", "Suya Chest"), activeIndex = 0),
                exercises = listOf(
                    InteractiveExercise(
                        id = "ha_2_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Order hot suya beef with extra spice:",
                        englishText = "Give me beef suya of one thousand Naira.",
                        targetText = "Ba ni suyan nama na naira dubu daya",
                        jumbledWords = listOf("na naira dubu daya", "Ba ni suyan nama"),
                        correctWordSequence = listOf("Ba ni suyan nama", "na naira dubu daya"),
                        explanation = "'Suya' is thinly sliced skewered meat spiced with yaji (kuli-kuli ginger pepper rub)."
                    )
                )
            )
        )
    }

    // --------------------------------------------------------------------------
    // 5. NIGERIAN PIDGIN UNITS
    // --------------------------------------------------------------------------
    private fun buildPidginUnits(lang: LanguageOption): List<LearningUnit> {
        return listOf(
            LearningUnit(
                id = "pcm_unit_1",
                unitNumber = 1,
                title = "How You Dey & Street Greetings",
                subtitle = "Master authentic Naija slang, greetings, and camaraderie",
                emoji = "🤙",
                themeColorHex = 0xFF58CC02,
                progressPercent = 80,
                nodes = createNodesForUnit("pcm_unit_1", listOf("How You Dey", "No Wahala", "Area Vibes", "Pidgin Quiz", "Chest"), activeIndex = 3),
                exercises = listOf(
                    InteractiveExercise(
                        id = "pcm_1_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Assemble the everyday Nigerian Pidgin greeting:",
                        englishText = "How are you? I am very fine, no problem.",
                        targetText = "How you dey ? I dey fine no wahala",
                        jumbledWords = listOf("no wahala", "I dey fine", "How you dey ?"),
                        correctWordSequence = listOf("How you dey ?", "I dey fine", "no wahala"),
                        explanation = "'How you dey ?' is the quintessential Nigerian greeting everywhere!"
                    ),
                    InteractiveExercise(
                        id = "pcm_1_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match Nigerian Pidgin slangs with English:",
                        pairItems = listOf(
                            PairMatchItem("1", "No wahala", "No trouble / No problem"),
                            PairMatchItem("2", "Abeg", "Please"),
                            PairMatchItem("3", "E go better", "Things will improve"),
                            PairMatchItem("4", "I dey kampe", "I am standing strong")
                        )
                    )
                )
            ),
            LearningUnit(
                id = "pcm_unit_2",
                unitNumber = 2,
                title = "Chop Life & Lagos Buka",
                subtitle = "Order pepper soup, pounded yam, roasted plantain (boli), and cold drinks",
                emoji = "🍲",
                themeColorHex = 0xFFFF9600,
                progressPercent = 25,
                nodes = createNodesForUnit("pcm_unit_2", listOf("At the Joint", "Pepper Soup Run", "Boli & Fish", "How Much E Be", "Chop Chest"), activeIndex = 1),
                exercises = listOf(
                    InteractiveExercise(
                        id = "pcm_2_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Order delicious food at a local spot:",
                        englishText = "Please give me hot catfish pepper soup.",
                        targetText = "Abeg give me hot point-and-kill pepper soup",
                        jumbledWords = listOf("pepper soup", "Abeg give me", "hot point-and-kill"),
                        correctWordSequence = listOf("Abeg give me", "hot point-and-kill", "pepper soup"),
                        explanation = "'Point and kill' refers to selecting a fresh live catfish at the restaurant pool."
                    )
                )
            )
        )
    }

    // --------------------------------------------------------------------------
    // 6. SPANISH UNITS
    // --------------------------------------------------------------------------
    private fun buildSpanishUnits(lang: LanguageOption): List<LearningUnit> {
        return listOf(
            LearningUnit(
                id = "es_unit_1",
                unitNumber = 1,
                title = "¡Hola! & Fundamentos",
                subtitle = "Master Spanish greetings, courtesy titles, and basic introductions",
                emoji = "🤝",
                themeColorHex = 0xFF58CC02,
                progressPercent = 70,
                nodes = createNodesForUnit("es_unit_1", listOf("Saludos", "Por favor", "Conversación", "Quiz Español", "Cofre"), activeIndex = 2),
                exercises = listOf(
                    InteractiveExercise(
                        id = "es_1_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Assemble the polite Spanish greeting:",
                        englishText = "Good morning, how are you today?",
                        targetText = "Buenos días ¿cómo estás hoy?",
                        jumbledWords = listOf("¿cómo estás hoy?", "Buenos días"),
                        correctWordSequence = listOf("Buenos días", "¿cómo estás hoy?"),
                        explanation = "'Buenos días' is used until the afternoon."
                    ),
                    InteractiveExercise(
                        id = "es_1_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match the Spanish polite phrases:",
                        pairItems = listOf(
                            PairMatchItem("1", "Por favor", "Please"),
                            PairMatchItem("2", "Muchas gracias", "Thank you very much"),
                            PairMatchItem("3", "Mucho gusto", "Nice to meet you"),
                            PairMatchItem("4", "Hasta luego", "See you later")
                        )
                    )
                )
            ),
            LearningUnit(
                id = "es_unit_2",
                unitNumber = 2,
                title = "Tapas, Comida & Restaurante",
                subtitle = "Order tapas, paella, drinks, and request the check like a local",
                emoji = "🥘",
                themeColorHex = 0xFFFF9600,
                progressPercent = 30,
                nodes = createNodesForUnit("es_unit_2", listOf("Bar de Tapas", "Pidiendo Paella", "Bebidas", "La Cuenta", "Cofre Sabroso"), activeIndex = 1),
                exercises = listOf(
                    InteractiveExercise(
                        id = "es_2_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Order politely at a tapas restaurant:",
                        englishText = "I would like a portion of tapas and water, please.",
                        targetText = "Quisiera una ración de tapas y agua por favor",
                        jumbledWords = listOf("por favor", "Quisiera una ración", "de tapas y agua"),
                        correctWordSequence = listOf("Quisiera una ración", "de tapas y agua", "por favor"),
                        explanation = "'Quisiera' or 'Me gustaría' is polite and standard."
                    ),
                    InteractiveExercise(
                        id = "es_2_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match Spanish food terms:",
                        pairItems = listOf(
                            PairMatchItem("1", "La cuenta", "The bill"),
                            PairMatchItem("2", "El camarero", "The waiter"),
                            PairMatchItem("3", "Un café con leche", "Coffee with milk"),
                            PairMatchItem("4", "Está delicioso", "It is delicious")
                        )
                    )
                )
            )
        )
    }

    // --------------------------------------------------------------------------
    // 7. GERMAN UNITS
    // --------------------------------------------------------------------------
    private fun buildGermanUnits(lang: LanguageOption): List<LearningUnit> {
        return listOf(
            LearningUnit(
                id = "de_unit_1",
                unitNumber = 1,
                title = "Hallo & Deutsche Grundlagen",
                subtitle = "Master greetings, politeness, and self introductions",
                emoji = "🤝",
                themeColorHex = 0xFF58CC02,
                progressPercent = 65,
                nodes = createNodesForUnit("de_unit_1", listOf("Hallo & Guten Tag", "Bitte & Danke", "Kennenlernen", "Quiz", "Schatzkiste"), activeIndex = 2),
                exercises = listOf(
                    InteractiveExercise(
                        id = "de_1_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Assemble the German greeting in order:",
                        englishText = "Good day, how are you?",
                        targetText = "Guten Tag wie geht es Ihnen ?",
                        jumbledWords = listOf("wie geht es Ihnen ?", "Guten Tag"),
                        correctWordSequence = listOf("Guten Tag", "wie geht es Ihnen ?"),
                        explanation = "'Wie geht es Ihnen ?' is polite and formal."
                    )
                )
            ),
            LearningUnit(
                id = "de_unit_2",
                unitNumber = 2,
                title = "Im Restaurant & Café",
                subtitle = "Order pretzels, schnitzel, coffee, and ask for the bill",
                emoji = "🥨",
                themeColorHex = 0xFFFF9600,
                progressPercent = 20,
                nodes = createNodesForUnit("de_unit_2", listOf("Bäckerei", "Im Gasthaus", "Kaffee & Kuchen", "Die Rechnung", "Schatzkiste"), activeIndex = 1),
                exercises = listOf(
                    InteractiveExercise(
                        id = "de_2_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Order politely in a German café:",
                        englishText = "I would like a coffee and a pretzel, please.",
                        targetText = "Ich möchte einen Kaffee und eine Brezel bitte",
                        jumbledWords = listOf("bitte", "Ich möchte", "einen Kaffee und eine Brezel"),
                        correctWordSequence = listOf("Ich möchte", "einen Kaffee und eine Brezel", "bitte"),
                        explanation = "'Ich möchte' means 'I would like'."
                    )
                )
            )
        )
    }

    // --------------------------------------------------------------------------
    // 8. JAPANESE UNITS
    // --------------------------------------------------------------------------
    private fun buildJapaneseUnits(lang: LanguageOption): List<LearningUnit> {
        return listOf(
            LearningUnit(
                id = "ja_unit_1",
                unitNumber = 1,
                title = "Konnichiwa & Japanese Politeness",
                subtitle = "Master greetings, bowing etiquette, and introductory phrases",
                emoji = "🎌",
                themeColorHex = 0xFF58CC02,
                progressPercent = 70,
                nodes = createNodesForUnit("ja_unit_1", listOf("Konnichiwa", "Arigatou", "Hajimemashite", "Kana Quiz", "Chest"), activeIndex = 2),
                exercises = listOf(
                    InteractiveExercise(
                        id = "ja_1_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Assemble the polite introductory phrase:",
                        englishText = "Nice to meet you, please treat me well.",
                        targetText = "Hajimemashite yoroshiku onegaishimasu",
                        jumbledWords = listOf("yoroshiku onegaishimasu", "Hajimemashite"),
                        correctWordSequence = listOf("Hajimemashite", "yoroshiku onegaishimasu"),
                        explanation = "'Hajimemashite' is said when meeting someone for the first time."
                    )
                )
            ),
            LearningUnit(
                id = "ja_unit_2",
                unitNumber = 2,
                title = "Ramen, Izakaya & Dining Out",
                subtitle = "Order steaming ramen, sushi, green tea, and ask for the bill",
                emoji = "🍜",
                themeColorHex = 0xFFFF9600,
                progressPercent = 25,
                nodes = createNodesForUnit("ja_unit_2", listOf("Ramen Shop", "Ordering Ocha", "Izakaya Treats", "O-kaikei (Bill)", "Chest"), activeIndex = 1),
                exercises = listOf(
                    InteractiveExercise(
                        id = "ja_2_jumble",
                        type = ExerciseType.WORD_JUMBLE,
                        prompt = "Order a bowl of ramen politely:",
                        englishText = "One bowl of ramen, please.",
                        targetText = "Ramen hitotsu kudasai",
                        jumbledWords = listOf("kudasai", "Ramen hitotsu"),
                        correctWordSequence = listOf("Ramen hitotsu", "kudasai"),
                        explanation = "'Hitotsu' means one item, and 'kudasai' means please give me."
                    )
                )
            )
        )
    }

    // --------------------------------------------------------------------------
    // 9. UNIVERSAL DYNAMIC UNITS FOR ALL OTHER WORLD LANGUAGES
    // --------------------------------------------------------------------------
    private fun buildUniversalUnits(lang: LanguageOption): List<LearningUnit> {
        val name = lang.name
        return listOf(
            LearningUnit(
                id = "${lang.code.lowercase()}_unit_1",
                unitNumber = 1,
                title = "Foundations & Essential Greetings",
                subtitle = "Master hellos, polite courtesies, and first impressions in $name",
                emoji = "🤝",
                themeColorHex = 0xFF58CC02,
                progressPercent = 65,
                nodes = createNodesForUnit("${lang.code.lowercase()}_unit_1", listOf("First Words", "Polite Expressions", "Dialogue", "Quiz", "Chest"), activeIndex = 2),
                exercises = listOf(
                    InteractiveExercise(
                        id = "${lang.code.lowercase()}_1_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Connect essential conversation phrases in $name:",
                        pairItems = listOf(
                            PairMatchItem("1", "Hello / Welcome", "Greeting"),
                            PairMatchItem("2", "Please", "Courtesy"),
                            PairMatchItem("3", "Thank you", "Gratitude"),
                            PairMatchItem("4", "Goodbye", "Farewell")
                        )
                    )
                )
            ),
            LearningUnit(
                id = "${lang.code.lowercase()}_unit_2",
                unitNumber = 2,
                title = "Food, Dining & Local Markets",
                subtitle = "Order meals, drinks, local delicacies, and request the check in $name",
                emoji = "🍽️",
                themeColorHex = 0xFFFF9600,
                progressPercent = 20,
                nodes = createNodesForUnit("${lang.code.lowercase()}_unit_2", listOf("At the Café", "Ordering Food", "Drinks & Water", "The Bill", "Feast Chest"), activeIndex = 1),
                exercises = listOf(
                    InteractiveExercise(
                        id = "${lang.code.lowercase()}_2_match",
                        type = ExerciseType.MATCH_PAIRS,
                        prompt = "Match the dining vocabulary in $name:",
                        pairItems = listOf(
                            PairMatchItem("1", "Menu", "Food choices"),
                            PairMatchItem("2", "Water", "Hydration"),
                            PairMatchItem("3", "Check / Bill", "Payment"),
                            PairMatchItem("4", "Delicious", "Compliment")
                        )
                    )
                )
            ),
            LearningUnit(
                id = "${lang.code.lowercase()}_unit_3",
                unitNumber = 3,
                title = "Travel, Transit & Directions",
                subtitle = "Ask for directions, take taxis, and navigate the city in $name",
                emoji = "🗺️",
                themeColorHex = 0xFF1CB0F6,
                progressPercent = 0,
                nodes = createNodesForUnit("${lang.code.lowercase()}_unit_3", listOf("Transit Hub", "Directions", "Taking Taxi", "Arrival", "Travel Chest"), activeIndex = 0)
            ),
            LearningUnit(
                id = "${lang.code.lowercase()}_unit_4",
                unitNumber = 4,
                title = "Numbers, Time & Shopping",
                subtitle = "Count 1-100, ask prices, and shop like a local in $name",
                emoji = "🔢",
                themeColorHex = 0xFFCE82FF,
                progressPercent = 0,
                nodes = createNodesForUnit("${lang.code.lowercase()}_unit_4", listOf("Counting", "Prices", "Clock & Time", "Shopping", "Gold Chest"), activeIndex = 0)
            )
        )
    }

    private fun createNodesForUnit(unitId: String, titles: List<String>, activeIndex: Int): List<PathNode> {
        val xOffsets = listOf(0, 48, 0, -48, 0)
        return titles.mapIndexed { index, title ->
            val state = when {
                index < activeIndex -> NodeState.COMPLETED
                index == activeIndex -> NodeState.ACTIVE
                index == activeIndex + 1 -> NodeState.UNLOCKED
                else -> NodeState.LOCKED
            }
            val type = when (index) {
                0 -> NodeType.LESSON
                1 -> NodeType.PHRASES
                2 -> NodeType.LESSON
                3 -> NodeType.QUIZ
                else -> NodeType.CHEST
            }
            PathNode(
                id = "${unitId}_node_$index",
                title = title,
                type = type,
                state = state,
                xOffsetDp = xOffsets.getOrElse(index) { 0 },
                hasCrown = index < activeIndex
            )
        }
    }
}
