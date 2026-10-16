package com.example.model

/**
 * Dedicated, culturally rich, authentic courses for Nigerian languages:
 * Yoruba, Igbo, Hausa, Nigerian Pidgin (Naija), Edo (Bini), Efik/Ibibio, Tiv, Fulfulde, Urhobo, Kanuri.
 */
object NigerianCourses {

    val nigerianLanguages: List<LanguageOption> = listOf(
        LanguageOption("YO", "YO A1", "🇳🇬", "Yoruba (Èdè Yorùbá)"),
        LanguageOption("IG", "IG A1", "🇳🇬", "Igbo (Asụsụ Igbo)"),
        LanguageOption("HA", "HA A1", "🇳🇬", "Hausa (Harshen Hausa)"),
        LanguageOption("PCM", "PCM A1", "🇳🇬", "Nigerian Pidgin (Naija)"),
        LanguageOption("EDO", "EDO A1", "🇳🇬", "Edo (Bini)"),
        LanguageOption("EFI", "EFI A1", "🇳🇬", "Efik / Ibibio"),
        LanguageOption("TIV", "TIV A1", "🇳🇬", "Tiv"),
        LanguageOption("FUL", "FUL A1", "🇳🇬", "Fulfulde (Fula)"),
        LanguageOption("URH", "URH A1", "🇳🇬", "Urhobo"),
        LanguageOption("KAU", "KAU A1", "🇳🇬", "Kanuri")
    )

    fun createYorubaCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Adebayo",
            tutorCatchphrase = "Ẹ n lẹ o! Ṣe o ti ṣetan lati kọ́ èdè Yorùbá lónìí?",
            unitTitle = "Ẹ n lẹ & Yorùbá Basics",
            unitSubtitle = "Master essential greetings, honorifics, and Lagos market dialogue",
            greetingNodeName = "Ìkíni (Greetings)",
            phrasesNodeName = "Gbólóhùn (Key Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "buka_lagos",
                    title = "At a Lagos Buka 🍛",
                    emoji = "🍛",
                    mission = "Order hot Jollof rice, plantain (dodo), and tender beef politely.",
                    initialFrench = "Ẹ wọlé o! Oúnjẹ gbígbóná wà lónìí. Kí ni kí n pèsè fún yín?",
                    initialEnglish = "Welcome inside! Hot food is ready today. What should I serve for you?",
                    suggestedReplies = listOf(
                        "Mo fẹ́ ìrẹsì jọlọọfu àti dodo 🍛",
                        "Ẹ jọ̀wọ́, pẹ̀lú ẹran dídín 🥩",
                        "Èló ni owó rẹ̀ gbogbo? 💳",
                        "Ẹ ṣeun púpọ̀, oúnjẹ dùn gan! ⭐"
                    )
                ),
                Scenario(
                    id = "balogun_market",
                    title = "At Balogun Market 🛍️",
                    emoji = "🛍️",
                    mission = "Bargain respectfully for goods at the famous Balogun market.",
                    initialFrench = "Ẹ káàárọ̀ o! Ẹkú àtijọ́. Kí ni ẹ fẹ́ ra lónìí ní ọjà Balógun?",
                    initialEnglish = "Good morning! Long time no see. What would you like to buy today at Balogun market?",
                    suggestedReplies = listOf(
                        "Ẹ jọ̀wọ́, èló ni àlùbọ́sà yìí? 🧅",
                        "Ẹ dín owó rẹ̀ kù díẹ̀, ẹ jọ̀wọ́ 🙏",
                        "Mo fẹ́ ra tòmátì púpọ̀ 🍅",
                        "Ẹ ṣe púpọ̀, o dábọ̀ o! 👋"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Báwo ni", "How are you? / Hello", "[bá-wo-ni]", "Báwo ni, ṣé àlàáfíà ni?", "How are you, are you doing well?", "Greetings"),
                ReviewCard("Ẹ jọ̀wọ́", "Please (respectful / honorific)", "[ɛ-d͡ʒɔ-wɔ́]", "Ẹ jọ̀wọ́, fún mi ní omi tútù.", "Please, give me cold drinking water.", "Politeness"),
                ReviewCard("Ẹ ṣe púpọ̀", "Thank you very much (respectful)", "[ɛ-ʃe-pú-pɔ̀]", "Ẹ ṣe púpọ̀ fún ìrànwọ́ yín.", "Thank you very much for your help.", "Gratitude"),
                ReviewCard("Ẹ káàárọ̀", "Good morning (respectful)", "[ɛ-káà-rɔ̀]", "Ẹ káàárọ̀ mà / sir.", "Good morning ma / sir.", "Daily Greetings"),
                ReviewCard("Kò tọ́pẹ́", "You're welcome / Don't mention it", "[kò-tɔ́-pɛ́]", "Kò tọ́pẹ́ rárá.", "You are very welcome.", "Courtesies"),
                ReviewCard("O dábọ̀", "Goodbye / Farewell", "[o-dá-bɔ̀]", "O dábọ̀, títí dìgbà mìíràn.", "Goodbye, until next time.", "Partings")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Respectful Greetings & The 'Ẹ' Prefix",
                    "In Yoruba culture, respect for elders and strangers is supreme. Always prefix verbs and greetings with 'Ẹ' when addressing an elder or a group (e.g., 'Ẹ káàárọ̀' instead of 'Káàárọ̀', and 'Ẹ ṣe púpọ̀' instead of 'O ṣe').",
                    listOf(
                        "Ẹ káàárọ̀" to "Good morning (polite/to elder)",
                        "Ẹ káàsán" to "Good afternoon",
                        "Ẹ kúùrọ̀lẹ́" to "Good evening",
                        "Ẹ ṣe púpọ̀" to "Thank you very much"
                    )
                ),
                GuidebookEntry(
                    "2. Everyday Courtesies & Market Etiquette",
                    "Friendly greetings always precede business transactions. Say 'Ẹ n lẹ o' upon arriving.",
                    listOf(
                        "Ẹ n lẹ o" to "Hello / Well done",
                        "Èló ni?" to "How much is it?",
                        "Ẹ jọ̀wọ́" to "Please",
                        "Àlàáfíà" to "Peace / Wellness"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you respectfully say 'Thank you very much' to an elder in Yoruba?",
                    "Ẹ ṣe púpọ̀",
                    listOf("O dábọ̀", "Ẹ ṣe púpọ̀", "Kò tọ́pẹ́"),
                    1,
                    "'Ẹ ṣe púpọ̀' uses the respectful 'Ẹ' honorific to thank an elder."
                ),
                QuizQuestion(
                    "What is the Yoruba greeting for 'Good morning'?",
                    "Ẹ káàárọ̀",
                    listOf("Ẹ káàárọ̀", "Ẹ kúùrọ̀lẹ́", "Ẹ káàsán"),
                    0,
                    "'Ẹ káàárọ̀' is the polite greeting for the morning."
                ),
                QuizQuestion(
                    "What does 'Báwo ni' mean?",
                    "How are you? / Hello",
                    listOf("Goodbye", "How are you? / Hello", "Please give me food"),
                    1,
                    "'Báwo ni' is the friendly Yoruba everyday greeting."
                )
            )
        )
    }

    fun createIgboCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Chidimma",
            tutorCatchphrase = "Nnọọ! Ka anyị mụta asụsụ Igbo taa!",
            unitTitle = "Nnọọ & Asụsụ Igbo Basics",
            unitSubtitle = "Master Igbo greetings, warm hospitality, and everyday conversation",
            greetingNodeName = "Ekele (Greetings)",
            phrasesNodeName = "Ahịrịokwu (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "ogbete_market",
                    title = "At Ogbete Market, Enugu 🛍️",
                    emoji = "🛍️",
                    mission = "Greet sellers warmly and shop for traditional groceries in Igbo.",
                    initialFrench = "Ụtụtụ ọma! Nnọọ na ahịa Ogbete. Kedu ihe ị na-achọ taa?",
                    initialEnglish = "Good morning! Welcome to Ogbete market. What are you looking for today?",
                    suggestedReplies = listOf(
                        "Ụtụtụ ọma! Kedu ka ahịa si aga? 🛍️",
                        "Biko, ego ole ka nke a bụ? 💰",
                        "Dalu rinne maka enyemaka gị 🙏",
                        "Ka omesia! 👋"
                    )
                ),
                Scenario(
                    id = "igbo_restaurant",
                    title = "At a Traditional Kitchen 🍲",
                    emoji = "🍲",
                    mission = "Order ofe onugbu (bitterleaf soup) with hot fufu.",
                    initialFrench = "Ndewo nnoo! Anyị nwere nri ọhụrụ na-atọ ụtọ. Kedu nri ị ga-eri?",
                    initialEnglish = "Greetings and welcome! We have delicious fresh food. What will you eat?",
                    suggestedReplies = listOf(
                        "Biko nye m ofe onugbu na fufu 🍲",
                        "Biko nye m mmiri ọṅụṅụ 🥤",
                        "Nri a na-atọ ụtọ nke ukwuu! ⭐",
                        "Imela nke ukwuu maka nri a 🙏"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Nnọọ", "Welcome / Hello", "[n-nɔ́ɔ́]", "Nnọọ na ụlọ anyị.", "Welcome to our home.", "Greetings"),
                ReviewCard("Kedu ka ime?", "How are you?", "[kè-dù kà ì-mè]", "Kedu ka ime taa? Ọ dị mma.", "How are you today? I am fine.", "Greetings"),
                ReviewCard("Dalu / Imela", "Thank you", "[dà-lù]", "Dalu rinne maka enyemaka gị.", "Thank you very much for your help.", "Gratitude"),
                ReviewCard("Biko", "Please", "[bì-kò]", "Biko nye m nri.", "Please give me food.", "Politeness"),
                ReviewCard("Ụtụtụ ọma", "Good morning", "[ù-tù-tù ɔ̀-mā]", "Ụtụtụ ọma nna m.", "Good morning my father.", "Daily Greetings"),
                ReviewCard("Ka omesia", "Goodbye / See you later", "[kà ò-mè-sì-à]", "Ka omesia, nwee ezigbo ụbọchị.", "Goodbye, have a good day.", "Partings")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Igbo Greetings & Respect",
                    "Igbo people value warm, expressive greetings. 'Kedu?' literally asks 'How is it?', and the polite response is 'Ọ dị mma' (It is good / I am well).",
                    listOf(
                        "Nnọọ" to "Welcome / Hello",
                        "Ụtụtụ ọma" to "Good morning",
                        "Ehihie ọma" to "Good afternoon",
                        "Abalị ọma" to "Good night"
                    )
                ),
                GuidebookEntry(
                    "2. Expressing Gratitude and Requests",
                    "Use 'Biko' for 'Please' and 'Dalu' or 'Imela' for 'Thank you'.",
                    listOf(
                        "Biko" to "Please",
                        "Dalu" to "Thank you",
                        "Imela" to "Thank you / Well done",
                        "Ọ dị mma" to "It is fine / Good"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you say 'Thank you' in Igbo?",
                    "Dalu",
                    listOf("Biko", "Dalu", "Nnọọ"),
                    1,
                    "'Dalu' (or 'Imela') expresses gratitude in Igbo."
                ),
                QuizQuestion(
                    "What does 'Biko' mean?",
                    "Please",
                    listOf("Hello", "Please", "Goodbye"),
                    1,
                    "'Biko' is the polite Igbo word for 'Please'."
                ),
                QuizQuestion(
                    "How do you say 'Good morning' in Igbo?",
                    "Ụtụtụ ọma",
                    listOf("Ụtụtụ ọma", "Abalị ọma", "Ka omesia"),
                    0,
                    "'Ụtụtụ ọma' translates to 'Good morning'."
                )
            )
        )
    }

    fun createHausaCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Aminu",
            tutorCatchphrase = "Sannu! Mu koyi Harshen Hausa tare!",
            unitTitle = "Sannu & Hausa Basics",
            unitSubtitle = "Master northern greetings, polite courtesies, and trade dialogue",
            greetingNodeName = "Gaisuwa (Greetings)",
            phrasesNodeName = "Jimloli (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "kurmi_market",
                    title = "At Kurmi Market, Kano 🛍️",
                    emoji = "🛍️",
                    mission = "Bargain respectfully with merchants in the historic Kurmi market.",
                    initialFrench = "Sannu da zuwa Kasuwar Kurmi! Me kake nema yau?",
                    initialEnglish = "Welcome to Kurmi Market! What are you looking for today?",
                    suggestedReplies = listOf(
                        "Sannu! Ina neman kaya masu kyau 🧵",
                        "Don Allah nawa ne kudin wannan? 💰",
                        "Ka rage min kudi kadan, don Allah 🙏",
                        "Nagode kwarai da gaske! 👋"
                    )
                ),
                Scenario(
                    id = "mai_suya",
                    title = "At the Mai Suya Spot 🥩",
                    emoji = "🥩",
                    mission = "Order spiced, tender suya skewers with onions and yaji.",
                    initialFrench = "Maraba! Ga nama mai zafi da kamshi. Nawa zan yanka maka?",
                    initialEnglish = "Welcome! Here is hot, aromatic suya. How much should I slice for you?",
                    suggestedReplies = listOf(
                        "Don Allah yanka min na dubu daya 🥩",
                        "Sanya min albasa da yaji sosai 🧅",
                        "Naman yana da dadi kwarai! ⭐",
                        "Nagode sosai, sai an jima! 👋"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Sannu", "Hello / Greetings", "[sán-nù]", "Sannu da aiki!", "Greetings on your work!", "Greetings"),
                ReviewCard("Ina kwana?", "Good morning (How did you sleep?)", "[ì-ná kwá-nà]", "Ina kwana? Lafiya lau.", "Good morning? Very well.", "Daily Greetings"),
                ReviewCard("Nagode", "Thank you", "[nà-góː-déː]", "Nagode kwarai da gaske.", "Thank you very much indeed.", "Gratitude"),
                ReviewCard("Don Allah", "Please (For God's sake)", "[dòn àl-láh]", "Don Allah ka taimake ni.", "Please help me.", "Politeness"),
                ReviewCard("Lafiya lau", "Very well / In peace", "[lá-fí-yà láu]", "Yaya jiki? Lafiya lau.", "How is the body? Perfectly well.", "Responses"),
                ReviewCard("Sai an jima", "Goodbye / See you later", "[sài àn-jí-mà]", "Sai an jima, in Allah ya yarda.", "See you later, God willing.", "Partings")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Hausa Hospitality & Inquiries",
                    "Hausa greetings inquire about wellbeing and the household. 'Lafiya' (peace/health) is the core response.",
                    listOf(
                        "Sannu" to "Hello / Welcome",
                        "Ina kwana?" to "Good morning?",
                        "Ina wuni?" to "Good afternoon?",
                        "Lafiya lau" to "I am very well"
                    )
                ),
                GuidebookEntry(
                    "2. Courtesies and Buying",
                    "Say 'Don Allah' (Please) before asking prices, and 'Nagode' (Thank you) after.",
                    listOf(
                        "Don Allah" to "Please",
                        "Nagode" to "Thank you",
                        "Nawa ne?" to "How much is it?",
                        "Babu damuwa" to "No trouble / No problem"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you say 'Thank you' in Hausa?",
                    "Nagode",
                    listOf("Sannu", "Nagode", "Don Allah"),
                    1,
                    "'Nagode' is the Hausa word for 'Thank you'."
                ),
                QuizQuestion(
                    "What does 'Don Allah' mean?",
                    "Please",
                    listOf("Goodbye", "Please", "Water"),
                    1,
                    "'Don Allah' translates to 'Please'."
                ),
                QuizQuestion(
                    "How do you greet someone in the morning in Hausa?",
                    "Ina kwana?",
                    listOf("Ina kwana?", "Sai gobe", "Barka da yamma"),
                    0,
                    "'Ina kwana?' asks 'How was your sleep / Good morning?'."
                )
            )
        )
    }

    fun createNigerianPidginCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Osas",
            tutorCatchphrase = "How far! Make we run this Naija Pidgin lesson sharp-sharp!",
            unitTitle = "How Far & Naija Vibes",
            unitSubtitle = "Master Nigerian Pidgin — West Africa's vibrant, colorful street lingua franca",
            greetingNodeName = "How Far (Greetings)",
            phrasesNodeName = "Slang & Talk (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "mama_put_lagos",
                    title = "At a Lagos Amala Buka 🍲",
                    emoji = "🍲",
                    mission = "Order hot Amala, Gbegiri, and Ewedu with assorted meat like a local.",
                    initialFrench = "How far my person! Welcome to Mama Put. Wetin you wan chop this afternoon?",
                    initialEnglish = "Hello my friend! Welcome to Mama Put. What would you like to eat this afternoon?",
                    suggestedReplies = listOf(
                        "Abeg give me hot Amala and Ewedu with goat meat! 🍲",
                        "Put plenty pomo and shaki join am 🥩",
                        "How much be everything? 💳",
                        "Thank you jare, the food sweet die! ⭐"
                    )
                ),
                Scenario(
                    id = "danfo_bus_stop",
                    title = "Taking a Danfo Bus 🚌",
                    emoji = "🚌",
                    mission = "Communicate with the conductor and get down safely at your bus stop.",
                    initialFrench = "Oya enter with your change o! Mainland straight! Where you dey alight?",
                    initialEnglish = "Come in with exact change! Straight to Mainland! Where are you getting off?",
                    suggestedReplies = listOf(
                        "Conductor, I get exact change no worry! 💵",
                        "Owa o! Drop me for under bridge abeg 🚌",
                        "Thank you bro, carry go! 👋"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("How far?", "Hello / How are you doing?", "[háo-fǎ]", "How far? I dey fine, thank you.", "Hello! I am doing great, thank you.", "Greetings"),
                ReviewCard("No wahala", "No problem / Everything is fine", "[nò-wà-há-là]", "No wahala at all, we go sort am.", "No problem at all, we will sort it out.", "Expressions"),
                ReviewCard("Abeg", "Please / I beg", "[à-bɛ́ɡ]", "Abeg pass the water for me.", "Please pass the water to me.", "Politeness"),
                ReviewCard("Thank you jare", "Thank you very much / enthusiastically", "[θáŋk-jù-d͡ʒà-rè]", "Thank you jare for the support!", "Thank you so much for the support!", "Gratitude"),
                ReviewCard("Wetin dey?", "What's happening? / What's up?", "[wɛ́-tín-dè]", "Wetin dey happen for area?", "What is going on around here?", "Greetings"),
                ReviewCard("I dey loyal", "Respectful salute / At your service", "[àì-dè-lɔ́ɪ-àl]", "Chairman, I dey loyal always!", "Boss, my highest respect always!", "Respect")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. The Core of Naija Pidgin",
                    "Nigerian Pidgin is spoken by over 100 million people across Nigeria and West Africa. It is rhythmic, humorous, and expressive.",
                    listOf(
                        "How far?" to "Hello / How are you?",
                        "I dey" to "I am doing well / I am here",
                        "Wetin dey happen?" to "What's happening?",
                        "No wahala" to "No problem at all"
                    )
                ),
                GuidebookEntry(
                    "2. Politeness and Emphasis",
                    "Add 'abeg' for requests and 'o' at the end of sentences for friendly emphasis.",
                    listOf(
                        "Abeg" to "Please",
                        "Oya" to "Let's go / Come on",
                        "Sharp-sharp" to "Quickly / Right away",
                        "E don set" to "It is ready / perfect"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "What does 'No wahala' mean in Nigerian Pidgin?",
                    "No problem / Everything is fine",
                    listOf("Too much trouble", "No problem / Everything is fine", "I don't know"),
                    1,
                    "'No wahala' is the beloved Nigerian expression meaning 'no worries / no problem'."
                ),
                QuizQuestion(
                    "How do you say 'Please' in Pidgin?",
                    "Abeg",
                    listOf("Oya", "Abeg", "How far"),
                    1,
                    "'Abeg' (short for 'I beg') is used universally for 'Please'."
                ),
                QuizQuestion(
                    "Translate 'How far?'",
                    "How are you? / What's up?",
                    listOf("Where are you going?", "How are you? / What's up?", "How much is it?"),
                    1,
                    "'How far?' is the quintessential Nigerian greeting."
                )
            )
        )
    }

    fun createEdoCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Osaze",
            tutorCatchphrase = "Kóyo! Ọb'ókhián gbe vbe èdè Edo!",
            unitTitle = "Kóyo & Edo Heritage",
            unitSubtitle = "Learn Edo (Bini) — Royal greetings, ancestral respect, and culture",
            greetingNodeName = "Ọ́b'ókhián (Greetings)",
            phrasesNodeName = "Ẹmwẹ (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "benin_heritage",
                    title = "At the Benin Kingdom Square 🏛️",
                    emoji = "🏛️",
                    mission = "Greet elders and cultural guides in authentic Edo language.",
                    initialFrench = "Kóyo! Ób'ókhián gbe. Vbe u ye lónà?",
                    initialEnglish = "Greetings! You are warmly welcome. How are you today?",
                    suggestedReplies = listOf(
                        "Ọb'ówie! Ọy'ẹmwẹ vbe rre 🏛️",
                        "Lahor, vbe orere na ye? 🗺️",
                        "Úrúese kpataki! 🙏"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Kóyo", "Hello / Greetings", "[kó-jo]", "Kóyo, vbe u ye?", "Hello, how are you?", "Greetings"),
                ReviewCard("Ọb'ówie", "Good morning", "[ɔ́-bó-wje]", "Ọb'ówie, erha m.", "Good morning, my father.", "Daily Greetings"),
                ReviewCard("Úrúese", "Thank you", "[ú-rú-e-se]", "Úrúese kpataki.", "Thank you very much.", "Gratitude"),
                ReviewCard("Lahor", "Please", "[la-hor]", "Lahor gbe.", "Please do.", "Politeness"),
                ReviewCard("Ób'ókhián", "Welcome", "[ó-bó-khián]", "Ób'ókhián gbe.", "You are welcome.", "Courtesies")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Royal Edo Courtesies",
                    "Edo (Bini) is the language of the ancient Benin Kingdom with rich historical traditions.",
                    listOf(
                        "Kóyo" to "Hello",
                        "Ọb'ówie" to "Good morning",
                        "Úrúese" to "Thank you",
                        "Lahor" to "Please"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "What does 'Úrúese' mean in Edo?",
                    "Thank you",
                    listOf("Hello", "Thank you", "Goodbye"),
                    1,
                    "'Úrúese' means 'Thank you' in Edo."
                ),
                QuizQuestion(
                    "What is the Edo greeting for 'Hello'?",
                    "Kóyo",
                    listOf("Kóyo", "Lahor", "Ób'ókhián"),
                    0,
                    "'Kóyo' is the classic Edo greeting."
                )
            )
        )
    }

    fun createEfikCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Aniefiok",
            tutorCatchphrase = "Mọkọm! Emesiere o, let's learn Efik and Ibibio!",
            unitTitle = "Mọkọm & Efik / Ibibio",
            unitSubtitle = "Master Cross River warmth, hospitality, and daily expressions",
            greetingNodeName = "Mọkọm (Greetings)",
            phrasesNodeName = "Ikọ (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "calabar_culinary",
                    title = "At a Calabar Kitchen 🍲",
                    emoji = "🍲",
                    mission = "Order world-famous Afang soup with pounded yam in Efik.",
                    initialFrench = "Emesiere! Idem mfo? Welcome to Calabar kitchen. Wetin you go chop?",
                    initialEnglish = "Good morning! How are you? Welcome to Calabar kitchen. What will you eat?",
                    suggestedReplies = listOf(
                        "Emesiere! Idem mi ọsọn̄ 🍲",
                        "Mbọk no mi Afang soup 🌿",
                        "Sọsọñọ eti-eti! 🙏"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("Mọkọm", "Greetings / Hello", "[mɔ-kɔm]", "Mọkọm o!", "Greetings to you!", "Greetings"),
                ReviewCard("Emesiere", "Good morning", "[e-me-sje-re]", "Emesiere ete mi.", "Good morning my father.", "Daily Greetings"),
                ReviewCard("Sọsọñọ", "Thank you", "[sɔ-sɔ-ɲɔ]", "Sọsọñọ eti-eti.", "Thank you very much.", "Gratitude"),
                ReviewCard("Mbọk", "Please", "[m-bɔk]", "Mbọk n̄wam mi.", "Please help me.", "Politeness"),
                ReviewCard("Idem mfo?", "How are you?", "[i-dem m-fo]", "Idem mfo? Idem mi ọsọn̄.", "How are you? I am strong/well.", "Greetings")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Efik & Ibibio Greetings",
                    "Efik speakers in Calabar and Akwa Ibom greet with 'Emesiere' (Good morning) and 'Sọsọñọ' (Thank you).",
                    listOf(
                        "Mọkọm" to "Greetings / Hello",
                        "Emesiere" to "Good morning",
                        "Sọsọñọ" to "Thank you",
                        "Mbọk" to "Please"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you say 'Thank you' in Efik?",
                    "Sọsọñọ",
                    listOf("Mbọk", "Sọsọñọ", "Mọkọm"),
                    1,
                    "'Sọsọñọ' means 'Thank you' in Efik."
                ),
                QuizQuestion(
                    "What does 'Emesiere' mean?",
                    "Good morning",
                    listOf("Good morning", "Goodbye", "Water"),
                    0,
                    "'Emesiere' is 'Good morning' in Efik."
                )
            )
        )
    }

    fun createTivCourse(lang: LanguageOption): LanguageCourse {
        return LanguageCourse(
            option = lang,
            tutorName = "Terver",
            tutorCatchphrase = "M sugh u! Welcome to learning Tiv from Benue!",
            unitTitle = "M sugh u & Tiv Basics",
            unitSubtitle = "Master Benue state's rich Tiv language, culture, and hospitality",
            greetingNodeName = "Msugh (Greetings)",
            phrasesNodeName = "Kwaghyan (Phrases)",
            scenarios = listOf(
                Scenario(
                    id = "makurdi_welcome",
                    title = "In Makurdi, Benue State 🌾",
                    emoji = "🌾",
                    mission = "Greet locals in Makurdi and practice essential Tiv dialogue.",
                    initialFrench = "M sugh u! U nder nena? Welcome to the Food Basket of the Nation!",
                    initialEnglish = "I greet you! How did you wake? Welcome to the Food Basket of the Nation!",
                    suggestedReplies = listOf(
                        "M nder doodo, m sugh u! 🌾",
                        "M zamber, na m mngerem 🥤",
                        "M sugh kpishi! 🙏"
                    )
                )
            ),
            reviewCards = listOf(
                ReviewCard("M sugh u", "I greet you / Hello", "[m suɣ u]", "M sugh u kpishi.", "I greet you very much.", "Greetings"),
                ReviewCard("U nder nena?", "Good morning / How did you wake?", "[u n-der ne-na]", "U nder nena? M nder doodo.", "How did you wake? I woke well.", "Daily Greetings"),
                ReviewCard("M sugh kpishi", "Thank you very much", "[m suɣ kpi-ʃi]", "M sugh kpishi sha iwasen.", "Thank you very much for help.", "Gratitude"),
                ReviewCard("M zamber", "Please / I plead", "[m zam-ber]", "M zamber, wase m.", "Please help me.", "Politeness")
            ),
            guidebookEntries = listOf(
                GuidebookEntry(
                    "1. Tiv Greetings & Courtesies",
                    "The Tiv people of Benue greet each other with 'M sugh u' (I greet you).",
                    listOf(
                        "M sugh u" to "I greet you / Hello",
                        "U nder nena?" to "How did you wake? (Good morning)",
                        "M sugh kpishi" to "Thank you very much",
                        "M zamber" to "Please"
                    )
                )
            ),
            quizQuestions = listOf(
                QuizQuestion(
                    "How do you say 'Thank you very much' in Tiv?",
                    "M sugh kpishi",
                    listOf("M zamber", "M sugh kpishi", "M nder nena"),
                    1,
                    "'M sugh kpishi' expresses warm gratitude in Tiv."
                )
            )
        )
    }
}
