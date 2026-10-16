package com.example.model

data class GuidebookEntry(
    val title: String,
    val description: String,
    val phrases: List<Pair<String, String>> // Target text -> English translation
)

data class LanguageCourse(
    val option: LanguageOption,
    val tutorName: String,
    val tutorCatchphrase: String,
    val unitTitle: String,
    val unitSubtitle: String,
    val greetingNodeName: String,
    val phrasesNodeName: String,
    val scenarios: List<Scenario>,
    val reviewCards: List<ReviewCard>,
    val guidebookEntries: List<GuidebookEntry>,
    val quizQuestions: List<QuizQuestion>,
    val units: List<LearningUnit> = emptyList()
)

object LanguageCatalog {

    val allLanguages: List<LanguageOption> = listOf(
        // Nigerian Languages 🇳🇬
        LanguageOption("YO", "YO A1", "🇳🇬", "Yoruba (Èdè Yorùbá)"),
        LanguageOption("IG", "IG A1", "🇳🇬", "Igbo (Asụsụ Igbo)"),
        LanguageOption("HA", "HA A1", "🇳🇬", "Hausa (Harshen Hausa)"),
        LanguageOption("PCM", "PCM A1", "🇳🇬", "Nigerian Pidgin (Naija)"),
        LanguageOption("EDO", "EDO A1", "🇳🇬", "Edo (Bini)"),
        LanguageOption("EFI", "EFI A1", "🇳🇬", "Efik / Ibibio"),
        LanguageOption("TIV", "TIV A1", "🇳🇬", "Tiv"),
        LanguageOption("FUL", "FUL A1", "🇳🇬", "Fulfulde (Fula)"),
        LanguageOption("URH", "URH A1", "🇳🇬", "Urhobo"),
        LanguageOption("KAU", "KAU A1", "🇳🇬", "Kanuri"),

        // Popular & European
        LanguageOption("FR", "FR A1", "🇫🇷", "French"),
        LanguageOption("ES", "ES A1", "🇪🇸", "Spanish"),
        LanguageOption("DE", "DE A1", "🇩🇪", "German"),
        LanguageOption("IT", "IT A1", "🇮🇹", "Italian"),
        LanguageOption("PT", "PT A1", "🇵🇹", "Portuguese"),
        LanguageOption("NL", "NL A1", "🇳🇱", "Dutch"),
        LanguageOption("RU", "RU A1", "🇷🇺", "Russian"),
        LanguageOption("PL", "PL A1", "🇵🇱", "Polish"),
        LanguageOption("SV", "SV A1", "🇸🇪", "Swedish"),
        LanguageOption("NO", "NO A1", "🇳🇴", "Norwegian"),
        LanguageOption("DA", "DA A1", "🇩🇰", "Danish"),
        LanguageOption("FI", "FI A1", "🇫🇮", "Finnish"),
        LanguageOption("EL", "EL A1", "🇬🇷", "Greek"),
        LanguageOption("TR", "TR A1", "🇹🇷", "Turkish"),
        LanguageOption("UK", "UK A1", "🇺🇦", "Ukrainian"),
        LanguageOption("CS", "CS A1", "🇨🇿", "Czech"),
        LanguageOption("HU", "HU A1", "🇭🇺", "Hungarian"),
        LanguageOption("RO", "RO A1", "🇷🇴", "Romanian"),
        LanguageOption("GA", "GA A1", "🇮🇪", "Irish (Gaeilge)"),
        LanguageOption("CY", "CY A1", "🏴󠁧󠁢󠁷󠁬󠁳󠁿", "Welsh (Cymraeg)"),
        LanguageOption("GD", "GD A1", "🏴󠁧󠁢󠁳󠁣󠁴󠁿", "Scottish Gaelic"),
        LanguageOption("HR", "HR A1", "🇭🇷", "Croatian"),
        LanguageOption("BG", "BG A1", "🇧🇬", "Bulgarian"),

        // Asia & Pacific
        LanguageOption("JA", "JA N5", "🇯🇵", "Japanese"),
        LanguageOption("ZH", "ZH HSK1", "🇨🇳", "Mandarin Chinese"),
        LanguageOption("YUE", "YUE A1", "🇭🇰", "Cantonese"),
        LanguageOption("KO", "KO A1", "🇰🇷", "Korean"),
        LanguageOption("HI", "HI A1", "🇮🇳", "Hindi"),
        LanguageOption("VI", "VI A1", "🇻🇳", "Vietnamese"),
        LanguageOption("ID", "ID A1", "🇮🇩", "Indonesian"),
        LanguageOption("TH", "TH A1", "🇹🇭", "Thai"),
        LanguageOption("TL", "TL A1", "🇵🇭", "Tagalog (Filipino)"),
        LanguageOption("MS", "MS A1", "🇲🇾", "Malay"),
        LanguageOption("BN", "BN A1", "🇧🇩", "Bengali"),
        LanguageOption("TA", "TA A1", "🇮🇳", "Tamil"),
        LanguageOption("TE", "TE A1", "🇮🇳", "Telugu"),
        LanguageOption("UR", "UR A1", "🇵🇰", "Urdu"),
        LanguageOption("PA", "PA A1", "🇮🇳", "Punjabi"),

        // Middle East & Africa
        LanguageOption("AR", "AR A1", "🇸🇦", "Arabic"),
        LanguageOption("HE", "HE A1", "🇮🇱", "Hebrew"),
        LanguageOption("FA", "FA A1", "🇮🇷", "Persian (Farsi)"),
        LanguageOption("SW", "SW A1", "🇰🇪", "Swahili"),
        LanguageOption("ZU", "ZU A1", "🇿🇦", "Zulu"),
        LanguageOption("XH", "XH A1", "🇿🇦", "Xhosa"),
        LanguageOption("AM", "AM A1", "🇪🇹", "Amharic"),
        LanguageOption("SO", "SO A1", "🇸🇴", "Somali"),
        LanguageOption("AF", "AF A1", "🇿🇦", "Afrikaans"),

        // Americas, Classical & World
        LanguageOption("PT_BR", "PT-BR A1", "🇧🇷", "Brazilian Portuguese"),
        LanguageOption("ES_MX", "ES-MX A1", "🇲🇽", "Mexican Spanish"),
        LanguageOption("LA", "LA A1", "🏛️", "Latin"),
        LanguageOption("EO", "EO A1", "🌐", "Esperanto"),
        LanguageOption("CA", "CA A1", "🇪🇸", "Catalan"),
        LanguageOption("EU", "EU A1", "🇪🇸", "Basque (Euskara)"),
        LanguageOption("IS", "IS A1", "🇮🇸", "Icelandic"),
        LanguageOption("HT", "HT A1", "🇭🇹", "Haitian Creole"),
        LanguageOption("QU", "QU A1", "🇵🇪", "Quechua"),
        LanguageOption("GN", "GN A1", "🇵🇾", "Guarani"),
        LanguageOption("GRC", "GRC A1", "🏛️", "Ancient Greek")
    )

    fun getCourseForLanguage(lang: LanguageOption): LanguageCourse {
        val baseCourse = when (lang.code.uppercase()) {
            // Nigerian Languages
            "YO" -> NigerianCourses.createYorubaCourse(lang)
            "IG" -> NigerianCourses.createIgboCourse(lang)
            "HA" -> NigerianCourses.createHausaCourse(lang)
            "PCM" -> NigerianCourses.createNigerianPidginCourse(lang)
            "EDO" -> NigerianCourses.createEdoCourse(lang)
            "EFI" -> NigerianCourses.createEfikCourse(lang)
            "TIV" -> NigerianCourses.createTivCourse(lang)

            // Other World Languages
            "ES", "ES_MX" -> createSpanishCourse(lang)
            "DE" -> createGermanCourse(lang)
            "IT" -> createItalianCourse(lang)
            "JA" -> createJapaneseCourse(lang)
            "ZH" -> createMandarinCourse(lang)
            "KO" -> createKoreanCourse(lang)
            "PT", "PT_BR" -> createPortugueseCourse(lang)
            "AR" -> createArabicCourse(lang)
            "RU" -> createRussianCourse(lang)
            "HI" -> createHindiCourse(lang)
            "NL" -> createDutchCourse(lang)
            "SV" -> createSwedishCourse(lang)
            "PL" -> createPolishCourse(lang)
            "TR" -> createTurkishCourse(lang)
            "VI" -> createVietnameseCourse(lang)
            "SW" -> createSwahiliCourse(lang)
            "EL" -> createGreekCourse(lang)
            "FR" -> createFrenchCourse(lang)
            else -> createDynamicCourse(lang)
        }
        val multiUnits = CurriculumBuilder.buildUnitsForLanguage(lang)
        return baseCourse.copy(units = multiUnits)
    }

    private fun createFrenchCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Camille",
            tutorCatchphrase = "Prêt pour pratiquer les salutations ?",
            unitTitle = "Bonjour & Basics",
            unitSubtitle = "Master essential greetings and introductions",
            greetingNodeName = "Greetings",
            phrasesNodeName = "Basic Phrases",
            scenarios = listOf(
                Scenario(
                    id = "bistro",
                    title = "At a Parisian Bistro",
                    emoji = "🥐",
                    mission = "Order a croissant and a café au lait naturally.",
                    initialFrench = "Bonjour ! Bienvenue au Bistro des Étoiles. Qu'est-ce que je vous sers aujourd'hui ?",
                    initialEnglish = "Hello! Welcome to Bistro des Étoiles. What can I get for you today?",
                    suggestedReplies = listOf(
                        "Oui, avec un peu de sucre ☕",
                        "Non merci, sans sucre",
                        "Combien ça coûte ? 💶",
                        "L'addition, s'il vous plaît 💳"
                    )
                ),
                Scenario(
                    id = "hotel",
                    title = "Checking into a Hotel",
                    emoji = "🏨",
                    mission = "Ask for your room key and Wi-Fi password.",
                    initialFrench = "Bonjour monsieur ! Avez-vous une réservation à l'Hôtel de la Paix ?",
                    initialEnglish = "Good day! Do you have a reservation at Hôtel de la Paix?",
                    suggestedReplies = listOf(
                        "Oui, au nom de Martin 🔑",
                        "Quel est le mot de passe du Wi-Fi ?",
                        "À quelle heure est le petit-déjeuner ?"
                    )
                ),
                Scenario(
                    id = "metro",
                    title = "Asking for Directions",
                    emoji = "🗺️",
                    mission = "Find the nearest Metro station to the Louvre.",
                    initialFrench = "Bonjour ! Vous semblez perdu. Où cherchez-vous à aller ?",
                    initialEnglish = "Hello! You look lost. Where are you trying to go?",
                    suggestedReplies = listOf(
                        "Où se trouve la station de métro ? 🚇",
                        "Est-ce que le Louvre est loin à pied ?",
                        "Merci beaucoup pour votre aide !"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Bonjour", "Hello / Good morning", "[bɔ̃.ʒuʁ]", "Bonjour, comment allez-vous ?", "Hello, how are you?", "Essential Greetings"),
                ReviewCard("S'il vous plaît", "Please (formal)", "[sil vu plɛ]", "Un café, s'il vous plaît.", "A coffee, please.", "Polite Expressions"),
                ReviewCard("Je voudrais", "I would like", "[ʒə vu.dʁɛ]", "Je voudrais une table pour deux.", "I would like a table for two.", "Ordering & Requests"),
                ReviewCard("Merci beaucoup", "Thank you very much", "[mɛʁ.si bo.ku]", "Merci beaucoup pour l'aide !", "Thank you very much for the help!", "Gratitude"),
                ReviewCard("L'addition", "The bill / check", "[la.di.sjɔ̃]", "L'addition, s'il vous plaît.", "The check, please.", "Dining Out"),
                ReviewCard("Enchanté", "Nice to meet you", "[ɑ̃.ʃɑ̃.te]", "Enchanté de faire votre connaissance.", "Nice to meet you.", "Introductions")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. French Greetings & Politeness",
                    "Always start any interaction with 'Bonjour' before ordering or asking questions.",
                    listOf(
                        "Bonjour" to "Good morning / Hello",
                        "Bonsoir" to "Good evening",
                        "S'il vous plaît" to "Please (polite / formal)",
                        "Merci beaucoup" to "Thank you very much"
                    )
                ),
                GuidebookEntry(
                    "2. Ordering at a Café",
                    "Use 'Je voudrais' (I would like) rather than 'Je veux' (I want) to sound natural and polite.",
                    listOf(
                        "Je voudrais un café" to "I would like a coffee",
                        "Un croissant, s'il vous plaît" to "A croissant, please",
                        "L'addition, s'il vous plaît" to "The check, please"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you politely order a coffee in French?",
                    "Je voudrais un café, s'il vous plaît.",
                    listOf("Je veux du café vite.", "Je voudrais un café, s'il vous plaît.", "Donne-moi un café maintenant."),
                    1,
                    "'Je voudrais' is polite and standard for ordering."
                ),
                QuizQuestion(
                    "What is the natural greeting for 'Good evening'?",
                    "Bonsoir !",
                    listOf("Bonjour", "Bonsoir", "Bonne nuit"),
                    1,
                    "'Bonsoir' is said upon meeting someone in the evening."
                ),
                QuizQuestion(
                    "Translate: 'Thank you very much'",
                    "Merci beaucoup !",
                    listOf("De rien", "Merci beaucoup", "S'il vous plaît"),
                    1,
                    "'Merci beaucoup' means 'Thank you very much'."
                )
            )
        )
    }

    private fun createSpanishCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Sofía",
            tutorCatchphrase = "¿Listo para practicar español hoy?",
            unitTitle = "¡Hola! & Fundamentos",
            unitSubtitle = "Domina los saludos esenciales y las presentaciones",
            greetingNodeName = "Saludos",
            phrasesNodeName = "Frases Básicas",
            scenarios = listOf(
                Scenario(
                    id = "cafe_madrid",
                    title = "En un Café en Madrid",
                    emoji = "☕",
                    mission = "Pide un café con leche y una tostada con amabilidad.",
                    initialFrench = "¡Hola! Bienvenido al Café del Prado. ¿Qué le pongo hoy?",
                    initialEnglish = "Hello! Welcome to Café del Prado. What can I get you today?",
                    suggestedReplies = listOf(
                        "Un café con leche, por favor ☕",
                        "Sin azúcar, muchas gracias",
                        "¿Cuánto cuesta en total? 💶",
                        "La cuenta, por favor 💳"
                    )
                ),
                Scenario(
                    id = "hotel_sevilla",
                    title = "En el Hotel en Sevilla",
                    emoji = "🏨",
                    mission = "Pide la llave de la habitación y la contraseña del Wi-Fi.",
                    initialFrench = "¡Buenos días! ¿Tiene una reserva a su nombre?",
                    initialEnglish = "Good morning! Do you have a reservation under your name?",
                    suggestedReplies = listOf(
                        "Sí, a nombre de Alex 🔑",
                        "¿Cuál es la contraseña del Wi-Fi?",
                        "¿A qué hora sirven el desayuno?"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("¡Hola!", "Hello / Hi", "[ˈo.la]", "¡Hola! ¿Cómo estás?", "Hello! How are you?", "Saludos"),
                ReviewCard("Por favor", "Please", "[poɾ faˈβoɾ]", "Un agua mineral, por favor.", "A mineral water, please.", "Cortesía"),
                ReviewCard("Muchas gracias", "Thank you very much", "[ˈmu.t͡ʃas ˈɣɾa.sjas]", "¡Muchas gracias por su ayuda!", "Thank you very much for your help!", "Gratitud"),
                ReviewCard("Quisiera", "I would like", "[kiˈsje.ɾa]", "Quisiera un café solo.", "I would like an espresso.", "Pedidos"),
                ReviewCard("La cuenta", "The check / bill", "[la ˈkwen.ta]", "La cuenta, por favor.", "The check, please.", "Restaurante"),
                ReviewCard("Mucho gusto", "Nice to meet you", "[ˈmu.t͡ʃo ˈɣws.to]", "Mucho gusto en conocerte.", "Nice to meet you.", "Presentaciones")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Saludos y Cortesía",
                    "En español, 'por favor' y 'muchas gracias' son indispensables para una buena conversación.",
                    listOf(
                        "¡Buenos días!" to "Good morning!",
                        "¡Buenas tardes!" to "Good afternoon!",
                        "Por favor" to "Please",
                        "Muchas gracias" to "Thank you very much"
                    )
                ),
                GuidebookEntry(
                    "2. Pedir en un Restaurante",
                    "Usa 'Quisiera' o 'Me gustaría' para pedir de manera elegante y educada.",
                    listOf(
                        "Quisiera un café" to "I would like a coffee",
                        "La cuenta, por favor" to "The bill, please"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "¿Cómo pides un café de forma educada?",
                    "Quisiera un café, por favor.",
                    listOf("Dame café ahora.", "Quisiera un café, por favor.", "Quiero café ya."),
                    1,
                    "'Quisiera' es la forma más cortés y natural."
                ),
                QuizQuestion(
                    "¿Cómo se dice 'Thank you very much'?",
                    "Muchas gracias",
                    listOf("De nada", "Muchas gracias", "Por favor"),
                    1,
                    "'Muchas gracias' significa 'Thank you very much'."
                )
            )
        )
    }

    private fun createGermanCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Lukas",
            tutorCatchphrase = "Bereit, Deutsch zu lernen?",
            unitTitle = "Hallo & Grundlagen",
            unitSubtitle = "Meistere wichtige Begrüßungen und Höflichkeit",
            greetingNodeName = "Begrüßung",
            phrasesNodeName = "Grundphrasen",
            scenarios = listOf(
                Scenario(
                    id = "berlin_cafe",
                    title = "Im Berliner Café",
                    emoji = "🥨",
                    mission = "Bestelle einen Kaffee und eine Brezel höflich.",
                    initialFrench = "Hallo! Willkommen im Café Stern. Was darf ich Ihnen bringen?",
                    initialEnglish = "Hello! Welcome to Café Stern. What may I bring you?",
                    suggestedReplies = listOf(
                        "Einen Kaffee bitte ☕",
                        "Ohne Zucker, danke sehr",
                        "Was kostet das? 💶",
                        "Die Rechnung bitte 💳"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Hallo", "Hello", "[ˈhaloː]", "Hallo! Wie geht es dir?", "Hello! How are you?", "Begrüßung"),
                ReviewCard("Bitte", "Please / You're welcome", "[ˈbɪtə]", "Einen Kaffee, bitte.", "A coffee, please.", "Höflichkeit"),
                ReviewCard("Danke schön", "Thank you very much", "[ˈdaŋkə ʃøːn]", "Danke schön für Ihre Hilfe!", "Thank you very much for your help!", "Dankbarkeit"),
                ReviewCard("Ich möchte", "I would like", "[ɪç ˈmœçtə]", "Ich möchte eine Brezel.", "I would like a pretzel.", "Bestellen"),
                ReviewCard("Die Rechnung", "The bill", "[diː ˈʁɛçnʊŋ]", "Die Rechnung, bitte.", "The bill, please.", "Restaurant")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Begrüßung & Höflichkeit",
                    "'Bitte' und 'Danke' sind zentrale Höflichkeitswörter im Deutschen.",
                    listOf(
                        "Guten Morgen" to "Good morning",
                        "Guten Tag" to "Good day / Hello",
                        "Bitte" to "Please",
                        "Danke schön" to "Thank you very much"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "Wie bestellst du höflich einen Kaffee?",
                    "Ich möchte einen Kaffee, bitte.",
                    listOf("Gib mir Kaffee.", "Ich möchte einen Kaffee, bitte.", "Kaffee sofort."),
                    1,
                    "'Ich möchte ... bitte' ist die höfliche Standardformel."
                )
            )
        )
    }

    private fun createJapaneseCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Kenji (健二)",
            tutorCatchphrase = "日本語の練習を始めましょう！ (Let's practice Japanese!)",
            unitTitle = "Konnichiwa & Basics",
            unitSubtitle = "Master essential Japanese greetings and daily etiquette",
            greetingNodeName = "挨拶 (Greetings)",
            phrasesNodeName = "基本表現 (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "tokyo_cafe",
                    title = "At a Tokyo Café 🍵",
                    emoji = "🍵",
                    mission = "Order a matcha latte politely in Japanese.",
                    initialFrench = "いらっしゃいませ！ご注文はお決まりですか？",
                    initialEnglish = "Welcome! Are you ready to order?",
                    suggestedReplies = listOf(
                        "抹茶ラテをください 🍵",
                        "砂糖は入れないでください",
                        "いくらですか？ 💴",
                        "お会計をお願いします 💳"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("こんにちは", "Hello / Good afternoon", "[kon-ni-chi-wa]", "こんにちは、お元気ですか？", "Hello, how are you?", "Greetings"),
                ReviewCard("ありがとう", "Thank you", "[a-ri-ga-to-u]", "ありがとうございます！", "Thank you very much!", "Gratitude"),
                ReviewCard("お願いします", "Please (requesting)", "[o-ne-ga-i-shi-ma-su]", "これをお願いします。", "This one, please.", "Polite Request"),
                ReviewCard("すみません", "Excuse me / Sorry", "[su-mi-ma-sen]", "すみません、お水をください。", "Excuse me, water please.", "Daily Etiquette"),
                ReviewCard("お会計", "The bill / check", "[o-ka-i-ke-i]", "お会計をお願いします。", "Check, please.", "Dining")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Japanese Polite Expressions",
                    "'Sumimasen' is a versatile word meaning excuse me, pardon, or thank you.",
                    listOf(
                        "こんにちは (Konnichiwa)" to "Hello / Good day",
                        "ありがとうございます (Arigatou gozaimasu)" to "Thank you very much",
                        "お願いします (Onegaishimasu)" to "Please (polite request)"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you politely say 'Thank you very much' in Japanese?",
                    "ありがとうございます (Arigatou gozaimasu)",
                    listOf("さようなら (Sayounara)", "ありがとうございます (Arigatou gozaimasu)", "おはよう (Ohayou)"),
                    1,
                    "'Arigatou gozaimasu' is polite and appropriate everywhere."
                )
            )
        )
    }

    private fun createMandarinCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Mei (美玲)",
            tutorCatchphrase = "准备好一起练习中文了吗？ (Ready to practice Chinese?)",
            unitTitle = "你好 (Nǐ Hǎo) & Basics",
            unitSubtitle = "Master essential Mandarin greetings and tones",
            greetingNodeName = "问候 (Greetings)",
            phrasesNodeName = "日常常用语 (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "shanghai_teahouse",
                    title = "At a Shanghai Teahouse 🫖",
                    emoji = "🫖",
                    mission = "Order jasmine tea and dumplings politely.",
                    initialFrench = "您好！欢迎光临。请问今天喝什么茶？",
                    initialEnglish = "Hello! Welcome. What tea would you like to drink today?",
                    suggestedReplies = listOf(
                        "请给我一杯茉莉花茶 🍵",
                        "不用加糖，谢谢",
                        "请问一共多少钱？ 💴",
                        "买单，谢谢 💳"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("你好", "Hello", "[nǐ hǎo]", "你好！很高兴认识你。", "Hello! Nice to meet you.", "问候"),
                ReviewCard("谢谢", "Thank you", "[xiè xie]", "非常感谢您的帮助！", "Thank you very much for your help!", "礼貌"),
                ReviewCard("请", "Please", "[qǐng]", "请问洗手间在哪里？", "Please, where is the restroom?", "礼貌"),
                ReviewCard("多少钱", "How much is it?", "[duō shao qián]", "这个一共多少钱？", "How much is this in total?", "购物")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Mandarin Politeness",
                    "'Nǐ hǎo' is the standard polite greeting used across all Chinese-speaking regions.",
                    listOf(
                        "你好 (Nǐ hǎo)" to "Hello",
                        "谢谢 (Xièxie)" to "Thank you",
                        "不客气 (Bú kèqi)" to "You are welcome"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you say 'Thank you' in Mandarin?",
                    "谢谢 (Xièxie)",
                    listOf("再见 (Zàijiàn)", "谢谢 (Xièxie)", "对不起 (Duìbuqǐ)"),
                    1,
                    "'Xièxie' means thank you."
                )
            )
        )
    }

    private fun createKoreanCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Minho (민호)",
            tutorCatchphrase = "한국어 대화 연습을 시작해볼까요? (Shall we practice Korean?)",
            unitTitle = "안녕하세요 & Basics",
            unitSubtitle = "Master polite Korean honorifics and daily speech",
            greetingNodeName = "인사말 (Greetings)",
            phrasesNodeName = "기본 표현 (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "seoul_cafe",
                    title = "At a Seoul Café ☕",
                    emoji = "☕",
                    mission = "Order an iced americano politely in Korean.",
                    initialFrench = "어서오세요! 무엇을 주문하시겠어요?",
                    initialEnglish = "Welcome! What would you like to order?",
                    suggestedReplies = listOf(
                        "아이스 아메리카노 한 잔 주세요 ☕",
                        "시럽은 빼주세요, 감사합니다",
                        "얼마예요? ₩",
                        "계산해 주세요 💳"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("안녕하세요", "Hello", "[an-nyeong-ha-se-yo]", "안녕하세요! 만나서 반갑습니다.", "Hello! Nice to meet you.", "인사"),
                ReviewCard("감사합니다", "Thank you", "[gam-sa-ham-ni-da]", "도와주셔서 감사합니다!", "Thank you for helping!", "감사"),
                ReviewCard("주세요", "Please give me", "[ju-se-yo]", "물 한 잔 주세요.", "Water please.", "주문"),
                ReviewCard("얼마예요", "How much is it?", "[eol-ma-ye-yo]", "이거 얼마예요?", "How much is this?", "쇼핑")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Korean Polite Endings",
                    "Ending sentences with '-yo' or '-nida' shows natural politeness and respect.",
                    listOf(
                        "안녕하세요 (Annyeonghaseyo)" to "Hello (Polite)",
                        "감사합니다 (Gamsahamnida)" to "Thank you (Formal)",
                        "주세요 (Juseyo)" to "Please give me"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you politely say 'Thank you' in Korean?",
                    "감사합니다 (Gamsahamnida)",
                    listOf("죄송합니다 (Joesonghamnida)", "감사합니다 (Gamsahamnida)", "안녕히 가세요 (Annyeonghi gaseyo)"),
                    1,
                    "'Gamsahamnida' is the polite standard way to say thank you."
                )
            )
        )
    }

    private fun createItalianCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Giulia",
            tutorCatchphrase = "Pronto per imparare l'italiano?",
            unitTitle = "Ciao & Fondamentali",
            unitSubtitle = "Padroneggia i saluti e le espressioni essenziali",
            greetingNodeName = "Saluti",
            phrasesNodeName = "Frasi Utili",
            scenarios = listOf(
                Scenario(
                    id = "roma_bar",
                    title = "Al Bar a Roma ☕",
                    emoji = "☕",
                    mission = "Ordina un espresso e un cornetto con naturalezza.",
                    initialFrench = "Buongiorno! Benvenuto al Bar Roma. Cosa posso portarle oggi?",
                    initialEnglish = "Good morning! Welcome to Bar Roma. What can I get you today?",
                    suggestedReplies = listOf(
                        "Un espresso, per favore ☕",
                        "Senza zucchero, grazie",
                        "Quanto costa in totale? 💶",
                        "Il conto, per favore 💳"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Ciao", "Hello / Goodbye", "[ˈtʃaː.o]", "Ciao, come stai?", "Hello, how are you?", "Saluti"),
                ReviewCard("Per favore", "Please", "[per faˈvoː.re]", "Un cappuccino, per favore.", "A cappuccino, please.", "Cortesia"),
                ReviewCard("Grazie mille", "Thank you very much", "[ˈɡrat.tsje ˈmil.le]", "Grazie mille per l'aiuto!", "Thank you very much for the help!", "Gratitudine"),
                ReviewCard("Vorrei", "I would like", "[vorˈrɛj]", "Vorrei un cornetto alla crema.", "I would like a cream croissant.", "Ordinare")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Saluti & Cortesia",
                    "Usa 'Buongiorno' al mattino e 'Buonasera' dal pomeriggio in poi.",
                    listOf(
                        "Buongiorno" to "Good morning / Hello",
                        "Per favore" to "Please",
                        "Grazie mille" to "Thank you very much"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "Come chiedi educatamente un caffè?",
                    "Vorrei un caffè, per favore.",
                    listOf("Dammi il caffè.", "Vorrei un caffè, per favore.", "Voglio caffè adesso."),
                    1,
                    "'Vorrei ... per favore' è la forma più educata."
                )
            )
        )
    }

    private fun createPortugueseCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Mateo",
            tutorCatchphrase = "Pronto para praticar português hoje?",
            unitTitle = "Olá & Fundamentos",
            unitSubtitle = "Aprenda saudações básicas e como se comunicar",
            greetingNodeName = "Saudações",
            phrasesNodeName = "Frases Básicas",
            scenarios = listOf(
                Scenario(
                    id = "lisboa_cafe",
                    title = "Num Café em Lisboa ☕",
                    emoji = "☕",
                    mission = "Peça um café e um pastel de nata com gentileza.",
                    initialFrench = "Bom dia! Bem-vindo ao Café Central. O que deseja hoje?",
                    initialEnglish = "Good day! Welcome to Café Central. What would you like today?",
                    suggestedReplies = listOf(
                        "Um café e um pastel de nata, por favor ☕",
                        "Sem açúcar, obrigado",
                        "Quanto custa? 💶",
                        "A conta, por favor 💳"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Olá", "Hello", "[ɔˈla]", "Olá, tudo bem?", "Hello, is everything good?", "Saudações"),
                ReviewCard("Por favor", "Please", "[puɾ fɐˈvoɾ]", "Uma água, por favor.", "A water, please.", "Cortesia"),
                ReviewCard("Muito obrigado", "Thank you very much", "[ˈmũj.tu ɔ.bɾiˈɡa.du]", "Muito obrigado pela ajuda!", "Thank you very much for the help!", "Gratidão"),
                ReviewCard("Eu gostaria", "I would like", "[ew ɡuʃ.tɐˈɾi.ɐ]", "Eu gostaria de um café.", "I would like a coffee.", "Pedidos")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Saudações Básicas",
                    "'Tudo bem?' é a forma mais amigável e comum de perguntar 'como vai?'.",
                    listOf(
                        "Bom dia" to "Good morning",
                        "Boa tarde" to "Good afternoon",
                        "Muito obrigado" to "Thank you very much"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "Como pedir educadamente um café em português?",
                    "Eu gostaria de um café, por favor.",
                    listOf("Dá-me café.", "Eu gostaria de um café, por favor.", "Quero café rápido."),
                    1,
                    "'Eu gostaria ... por favor' é a forma mais gentil e educada."
                )
            )
        )
    }

    private fun createArabicCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Amina (أمينة)",
            tutorCatchphrase = "هل أنت مستعد لممارسة اللغة العربية؟",
            unitTitle = "Marhaban & Basics",
            unitSubtitle = "Master polite Arabic greetings and hospitality phrases",
            greetingNodeName = "التحيات (Greetings)",
            phrasesNodeName = "العبارات الأساسية (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "dubai_cafe",
                    title = "At a Traditional Café ☕",
                    emoji = "☕",
                    mission = "Order Arabic coffee and dates politely.",
                    initialFrench = "أهلاً وسهلاً بك! ماذا تود أن تشرب اليوم؟",
                    initialEnglish = "Welcome! What would you like to drink today?",
                    suggestedReplies = listOf(
                        "قهوة عربية من فضلك ☕",
                        "بدون سكر، شكراً جزيلاً",
                        "كم الحساب من فضلك؟",
                        "شكراً جزيلاً على المساعدة"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("مرحباً", "Hello / Welcome", "[mar-ha-ban]", "مرحباً، كيف حالك؟", "Hello, how are you?", "Greetings"),
                ReviewCard("من فضلك", "Please", "[min fad-lik]", "فنجان قهوة من فضلك.", "A cup of coffee, please.", "Politeness"),
                ReviewCard("شكراً جزيلاً", "Thank you very much", "[shuk-ran ja-zee-lan]", "شكراً جزيلاً لك!", "Thank you very much to you!", "Gratitude"),
                ReviewCard("أريد", "I would like", "[u-ree-du]", "أريد ماءً من فضلك.", "I would like water please.", "Ordering")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Arabic Hospitality & Politeness",
                    "'Marhaban' and 'Ahlan wa Sahlan' are warm greetings that invite friendship and connection.",
                    listOf(
                        "مرحباً (Marhaban)" to "Hello",
                        "من فضلك (Min fadlik)" to "Please",
                        "شكراً (Shukran)" to "Thank you"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you say 'Thank you very much' in Arabic?",
                    "شكراً جزيلاً (Shukran jazeelan)",
                    listOf("عفواً (Afwan)", "شكراً جزيلاً (Shukran jazeelan)", "مع السلامة (Ma'a salama)"),
                    1,
                    "'Shukran jazeelan' translates to 'Thank you very much'."
                )
            )
        )
    }

    private fun createRussianCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Elena",
            tutorCatchphrase = "Готовы практиковать русский язык? (Ready to practice Russian?)",
            unitTitle = "Привет & Basics",
            unitSubtitle = "Master polite Russian greetings and everyday speech",
            greetingNodeName = "Приветствия (Greetings)",
            phrasesNodeName = "Основные фразы (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "cafe_moscow",
                    title = "At a Cozy Café ☕",
                    emoji = "☕",
                    mission = "Order hot tea and a blini politely.",
                    initialFrench = "Здравствуйте! Добро пожаловать. Что вам принести?",
                    initialEnglish = "Hello! Welcome. What can I bring you?",
                    suggestedReplies = listOf(
                        "Чай с лимоном, пожалуйста ☕",
                        "Без сахара, спасибо",
                        "Сколько это стоит? 💶",
                        "Счёт, пожалуйста 💳"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Здравствуйте", "Hello (formal)", "[zdrast-vuy-te]", "Здравствуйте, как ваши дела?", "Hello, how are you doing?", "Greetings"),
                ReviewCard("Пожалуйста", "Please / You're welcome", "[pa-zhal-oo-sta]", "Кофе, пожалуйста.", "Coffee, please.", "Politeness"),
                ReviewCard("Большое спасибо", "Thank you very much", "[bal-shoye spa-see-ba]", "Большое спасибо за помощь!", "Thank you very much for help!", "Gratitude")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Russian Politeness",
                    "'Здравствуйте' is the polite standard formal greeting suitable with teachers and strangers.",
                    listOf(
                        "Здравствуйте" to "Hello (formal)",
                        "Пожалуйста" to "Please / Welcome",
                        "Большое спасибо" to "Thank you very much"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you say 'Thank you very much' in Russian?",
                    "Большое спасибо (Bolshoye spasibo)",
                    listOf("До свидания (Do svidaniya)", "Большое спасибо (Bolshoye spasibo)", "Пожалуйста (Pozhaluysta)"),
                    1,
                    "'Bolshoye spasibo' means thank you very much."
                )
            )
        )
    }

    private fun createHindiCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Aarav",
            tutorCatchphrase = "नमस्ते! चलिए हिंदी सीखते हैं। (Let's learn Hindi!)",
            unitTitle = "नमस्ते & Basics",
            unitSubtitle = "Learn polite Hindi greetings and daily introductions",
            greetingNodeName = "अभिवादन (Greetings)",
            phrasesNodeName = "महत्वपूर्ण वाक्य (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "delhi_cafe",
                    title = "At a Chai Stall ☕",
                    emoji = "☕",
                    mission = "Order masala chai and samosa politely.",
                    initialFrench = "नमस्ते! आपका स्वागत है। आप क्या लेंगे?",
                    initialEnglish = "Namaste! Welcome. What will you have?",
                    suggestedReplies = listOf(
                        "एक कप मसाला चाय, कृपया ☕",
                        "कम चीनी, धन्यवाद",
                        "यह कितने का है? ₹",
                        "धन्यवाद!"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("नमस्ते", "Hello / Respectful greeting", "[na-mas-te]", "नमस्ते! आप कैसे हैं?", "Hello! How are you?", "Greetings"),
                ReviewCard("कृपया", "Please", "[kri-pa-ya]", "एक चाय, कृपया।", "A tea, please.", "Politeness"),
                ReviewCard("बहुत धन्यवाद", "Thank you very much", "[ba-hut dhan-ya-vaad]", "आपकी मदद के लिए बहुत धन्यवाद!", "Thank you very much for your help!", "Gratitude")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Hindi Greeting & Culture",
                    "'Namaste' conveys deep respect and goodwill, accompanied by a slight bow with folded hands.",
                    listOf(
                        "नमस्ते (Namaste)" to "Hello / Respectful greeting",
                        "कृपया (Kripaya)" to "Please",
                        "धन्यवाद (Dhanyavaad)" to "Thank you"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "What is the traditional polite greeting in Hindi?",
                    "नमस्ते (Namaste)",
                    listOf("अलविदा (Alvida)", "नमस्ते (Namaste)", "हां (Haan)"),
                    1,
                    "'Namaste' is the standard polite greeting."
                )
            )
        )
    }

    private fun createDutchCourse(lang: LanguageOption): LanguageCourse = createEuropeanGenericCourse(lang, "Daan", "Hallo", "Dank je wel", "Alstublieft", "Goedemorgen")
    private fun createSwedishCourse(lang: LanguageOption): LanguageCourse = createEuropeanGenericCourse(lang, "Astrid", "Hej", "Tack så mycket", "Snälla", "God morgon")
    private fun createPolishCourse(lang: LanguageOption): LanguageCourse = createEuropeanGenericCourse(lang, "Piotr", "Cześć", "Dziękuję bardzo", "Proszę", "Dzień dobry")
    private fun createTurkishCourse(lang: LanguageOption): LanguageCourse = createEuropeanGenericCourse(lang, "Emre", "Merhaba", "Çok teşekkür ederim", "Lütfen", "Günaydın")
    private fun createGreekCourse(lang: LanguageOption): LanguageCourse = createEuropeanGenericCourse(lang, "Nikos", "Γειά σας (Yia sas)", "Ευχαριστώ πολύ (Efharisto poli)", "Παρακαλώ (Parakalo)", "Καλημέρα (Kalimera)")
    private fun createVietnameseCourse(lang: LanguageOption): LanguageCourse = createAsianGenericCourse(lang, "Linh", "Xin chào", "Cảm ơn rất nhiều", "Làm ơn", "Chào buổi sáng")
    private fun createSwahiliCourse(lang: LanguageOption): LanguageCourse = createAfricanGenericCourse(lang, "Juma", "Habari", "Asante sana", "Tafadhali", "Habari za asubuhi")

    private fun createEuropeanGenericCourse(
        lang: LanguageOption,
        tutorName: String,
        hello: String,
        thankYou: String,
        please: String,
        goodMorning: String
    ): LanguageCourse {
        val langName = lang.name
        return LanguageCourse(
            option = lang,
            tutorName = tutorName,
            tutorCatchphrase = "Ready to master $langName conversations?",
            unitTitle = "$hello & $langName Basics",
            unitSubtitle = "Master essential $langName greetings and everyday dialogue",
            greetingNodeName = "Greetings",
            phrasesNodeName = "Key Phrases",
            scenarios = listOf(
                Scenario(
                    id = "cafe_${lang.code.lowercase()}",
                    title = "At a Local Café ☕",
                    emoji = "☕",
                    mission = "Order coffee and ask for the bill naturally.",
                    initialFrench = "$hello! Welcome. What can I get for you today?",
                    initialEnglish = "Hello! Welcome. What can I get for you today?",
                    suggestedReplies = listOf(
                        "A coffee, $please ☕",
                        "No sugar, $thankYou",
                        "How much is it?",
                        "The bill, $please 💳"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard(hello, "Hello", "[$hello]", "$hello! How are you?", "Hello! How are you?", "Greetings"),
                ReviewCard(please, "Please", "[$please]", "Coffee, $please.", "Coffee, please.", "Politeness"),
                ReviewCard(thankYou, "Thank you very much", "[$thankYou]", "$thankYou for the help!", "Thank you for the help!", "Gratitude"),
                ReviewCard(goodMorning, "Good morning", "[$goodMorning]", "$goodMorning!", "Good morning!", "Time greetings")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. $langName Etiquette",
                    "Polite greetings like '$hello' and '$thankYou' help you connect with native speakers immediately.",
                    listOf(
                        hello to "Hello / Good day",
                        please to "Please",
                        thankYou to "Thank you very much"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you say 'Thank you very much' in $langName?",
                    thankYou,
                    listOf("Goodbye", thankYou, "Excuse me"),
                    1,
                    "'$thankYou' expresses polite gratitude in $langName."
                )
            )
        )
    }

    private fun createAsianGenericCourse(
        lang: LanguageOption,
        tutorName: String,
        hello: String,
        thankYou: String,
        please: String,
        goodMorning: String
    ): LanguageCourse = createEuropeanGenericCourse(lang, tutorName, hello, thankYou, please, goodMorning)

    private fun createAfricanGenericCourse(
        lang: LanguageOption,
        tutorName: String,
        hello: String,
        thankYou: String,
        please: String,
        goodMorning: String
    ): LanguageCourse = createEuropeanGenericCourse(lang, tutorName, hello, thankYou, please, goodMorning)

    /**
     * Fallback dynamic course generator for ANY user-added language
     */
    fun createDynamicCourse(lang: LanguageOption): LanguageCourse {
        val langName = lang.name
        return LanguageCourse(
            option = lang,
            tutorName = "Polyglot AI",
            tutorCatchphrase = "Welcome to $langName! Let's explore real-world conversations together.",
            unitTitle = "Welcome to $langName",
            unitSubtitle = "Master essential $langName expressions and foundational vocabulary",
            greetingNodeName = "Greetings",
            phrasesNodeName = "Everyday Phrases",
            scenarios = listOf(
                Scenario(
                    id = "scenario_${lang.code.lowercase()}",
                    title = "Meeting a Local Friend 👋",
                    emoji = "👋",
                    mission = "Introduce yourself and practice basic greetings in $langName.",
                    initialFrench = "Hello and welcome to learning $langName! What would you like to practice today?",
                    initialEnglish = "Hello and welcome to learning $langName! What would you like to practice today?",
                    suggestedReplies = listOf(
                        "I want to practice greetings 👋",
                        "How do I say thank you in $langName?",
                        "Let's practice ordering food and drinks ☕",
                        "Tell me about daily life!"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Hello / Welcome", "Greeting in $langName", "[Greeting]", "Welcome to $langName study!", "Welcome to $langName study!", "Greetings"),
                ReviewCard("Thank you", "Gratitude in $langName", "[Thank you]", "Thank you very much!", "Thank you very much!", "Politeness"),
                ReviewCard("Please", "Requesting politely", "[Please]", "Please help me learn.", "Please help me learn.", "Requests")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Learning $langName",
                    "Consistency and active speaking are key to acquiring $langName naturally.",
                    listOf(
                        "Hello" to "Greetings in $langName",
                        "Thank you" to "Gratitude in $langName",
                        "Please" to "Polite requests"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "What is the best way to build confidence when speaking $langName?",
                    "Practice speaking aloud daily",
                    listOf("Never speak until fluent", "Practice speaking aloud daily", "Only study grammar without speaking"),
                    1,
                    "Consistent vocal practice and conversation builds natural fluency in any language."
                )
            )
        )
    }
}
