const mongoose = require('mongoose');
const Word = require('./models/Word');

const MONGO_URI = 'mongodb+srv://donyagh2580_db_user:Dxnyx20042005@cluster0.jcbb3wa.mongodb.net/CompLexDB?retryWrites=true&w=majority';

const sampleWords = [
    {
        englishWord: "Algorithm",
        definition: "A process or set of rules to be followed in calculations.",
        example: "We need a better algorithm to sort this data.",
        category: "Programming",
        difficulty: "Intermediate"
    },
    {
        englishWord: "Array",
        definition: "A data structure consisting of a collection of elements.",
        example: "The array contains 10 numbers.",
        category: "Data Structures",
        difficulty: "Beginner"
    },
    {
        englishWord: "Database",
        definition: "A structured set of data held in a computer.",
        example: "MongoDB is a popular NoSQL database.",
        category: "Databases",
        difficulty: "Beginner"
    }
];

const seedDatabase = async () => {
    try {
        await mongoose.connect(MONGO_URI);
        console.log('✅ به دیتابیس وصل شدیم.');

        await Word.deleteMany(); 
        console.log('🗑️ لغات قبلی پاک شدند.');

        await Word.insertMany(sampleWords);
        console.log('🌱 لغات جدید با موفقیت اضافه شدند!');

        process.exit();
    } catch (error) {
        console.error('❌ خطا در اضافه کردن دیتا:', error.message);
        process.exit(1);
    }
};

seedDatabase();