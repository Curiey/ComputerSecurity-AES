# ComputerSecurity-Assignment1-AES

בשאלה זו אתם צריכים לשבור צופן AES פשוט, המסומן ∗ 𝑆𝐸𝐴 . 3
בגרסההפשוטההזושלAESישנם3מפתחותשוניםהמוגדריםכ 𝐾1𝐾2𝐾3,השימושבמפתחותהללו ∗
הוא כפי שהם (כלומר אין שום מניפולציה על המפתחות). 𝐴𝐸𝑆3 מבצע 3 פעמים (איטרציות￼￼ ∗∗
iterations) של 𝐴𝐸𝑆1 אשר גם הוא גירסא פשוטה של צופן AES. 𝐴𝐸𝑆1 מוגדר כדלקמן:
Plain-text הודעה לא מוצפנת – M Cipher-text הודעה. מוצפנת – C K – מפתח הצפנה/פענוח
Definition of AES1*
AES1* is a single round implementation of AES that is defined as follows:
• AES1* (M)K = AddRoundKey(ShiftColumns(M),K) = C
• AES1*-1 (C)K = ShiftColumns-1(AddRoundKey(C,K)) = M
￼
הפונקציה )ShiftColumns(M עובדת בצורה זהה לפונקציה )ShiftRows(M שנלמדה בכיתה כאשר ההזזה הציקלית היא של עמודה (במקום שורה) למעלה (במקום שמאלה). העמודה באינדקס 0 זזה 0 תאים למעלה. העמודה באינדקס 1 זזה תא אחד למעלה. העמודה באינדקס 2.................
)ShiftColumns-1(M מבצעת את הפעולה ההפוכה, כאשר ההזזה הציקלית של כל עמודה היא כלפי מטה.
ולכן הגדרה של ∗𝑆𝐸𝐴 היא: 3
בהינתן הודעה M והודעה מוצפנת C כך ש: }𝑀{∗𝑆𝐸𝐴 = 𝐶 , הנכם צריכים לממש שיטה
}𝑀{∗𝑆𝐸𝐴 = 𝐶.￼
𝑘1,𝑘2,𝑘3 3
יעילה למציאת 3 המפתחות K1,K2,K3 המקיימת:
האלגוריתם הצפנה ∗𝑆𝐸𝐴 ממומש כך: 3
𝑘1,𝑘2,𝑘3 3
3∗𝑆𝐸𝐴￼
C1 C2
∗𝐴𝐸𝑆1￼￼￼￼￼
∗𝐴𝐸𝑆1
∗𝐴𝐸𝑆1
M
C
￼￼￼￼￼￼K1 K2 K3
שימו לב כי במימוש שלכם לפריצה של ∗𝑆𝐸𝐴 עליכם להתייחס ל ∗𝑆𝐸𝐴 כקופסא שחורה המקבלת 33
הודעה M ו3 מפתחות כקלט ומוציאה כפלט הודעה מוצפנת C לפי ההגדרה נ״ל. אינכם יכולים להשתמש בהודעות הביניים C1 ו C2! כמו כן מפתחות K1 K2 K3 חייבים להיות שונים אחד
מהשני
א. רשום פתרון תיאורטי לשיטה שאתה מציע (4 נק'). ב. ממש את הפתרון שהצעת ב- JAVA לפי הדגשים הבאים (14 נק'):
דגשיםלמימוש:
o הודעה M יכולה להיות ארוכה יותר מ 128 ביט, המימוש שלכם צריך לקחת בחשבון שאורך הודעה יכול להיות יותר ארוך מ 128 ביט, לחלק את ההודעה לבלוקים של 128 ביט ולהפעיל את האלגוריתם על כל בלוק, לשם הפשטות ניתן להניח כי אורך ההודעה היא מכפלה של 128 ביט.
o o o
o o o
o o o o o
–e : instruction to encrypt the input file –d: instruction to decrypt the input file
o עליכם לממש ממשק )interface( הצפנה/פענוח כדלקמן: ∗
–k <path>: path to the keys, the key should be 384 bit (128*3) for 𝐴𝐸𝑆3 . and should be divided into 3 separate keys.
–i <input filepath>: a path to a filewe want to encrypt/decrypt
–o <output filepath>: a path to the output file
Usage: Java –jar aes.jar -e/-d –k <path-to-key-file > -i <path-to-input-file> -o <path-to-output-file>
–e –k key.txt –i message.txt –o cypther.txt Java –jar aes.jar
עליכם לממש ממשק )interface( לשבירה של ההצפנה כדלקמן:
–b : instruction to break the encryption algorithm
–m <path>: denotes the path to the plain-textmessage –c <path>: denotes the path to the cipher-text message
–o <path>: a path to the output filewith the key(s) found.
Usage: Java –jar aes.jar -b –m <path-to-message> –c <path-to-cipher> -o < output-path>
פורמט הפלטים והקלטים: o הנכם מתבקשים לכתוב ולקרוא מקבצים בבתים Bytes ולא כטקסט. o שימולבלסדרבתים(Endianness),ניתןלוודאאתסדרהבתיםבשקופיותשלההרצאה. o השתמשו בקבצי בדיקה שסופקו לכם ביחד עם התרגיל על מנת לבדוק את התוכנית שלכם.
o
o
o o o o
o o
o
שימו לב כי זמן ריצה של התוכנית צריך להיות בזמן סביר הלא עולה מעל דקה אחת. אין להשתמש ב brute force. עליכם להגיש את כל קבצי המקור וקובץ jar מקומפל של התוכנית שלכם. הבדיקה מתבצעת בתוכנה אוטומטית, אנא בדקו היטב כי התוכנית שלכם עונה על כל הדרישות הנמצאות בקובץ הזה. שימו לב כי תוכנה אוטומטית תצליב בין כל קבצי המקור לזיהוי קוד דומה, אנא הימנעו מהעתקות. ההגשה היא במודל, יש להגיש קובץ zip בלבד בפורמט הבא: ass1_id1_id2.zip בתוך הקובץ יש לשים את כל קבצי המקור וקובץ jar. קובץ jar חייב להיות בשם aes.jar קבצי בדיקה ניתן להוריד מהקישור הבא
