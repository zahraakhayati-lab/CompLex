const User=require('../models/User');
const Word=require('../models/Word');
exports.searchWord=async(req,res) =>{
    try{
        const query=req.query.q;
        if(!query){
            return res.status(400).json({message:'لطفا کلمه ای برای جستجو وارد کنید'});

        }
        const words=await Word.find({
            englishWord:{$regex:query,$options:'i'}
        });
        if(words.length===0){
            return res.status(404).json({message:'لغتی بااین نام یافت نشد'});

        }
        res.status(200).json({count:words.length,words});

    }catch(err){
        res.status(500).json({message:'خطا در سرور',error:err.message});
    }
};
exports.getWordDetails=async(req,res) =>{
    try{
        const word =await Word.findById(req.params.id);
        if(!word){
            return res.status(404).json({message:'لغت پیدا نشد'});

        }
        res.status(200).json(word);
    }catch(err){
        res.status(500).json({message:'خطا در سرور',error:err.message});
    }
};
exports.getHomeWords = async (req,res) =>{
    try{
        const words=await Word.aggregate([{$sample:{size:1}}]);
        if(word.length==0){
            return res.status(404).json({message:'هیچ لغتی در دیتابیس وجود ندارد'});
        }
        res.status(200).json(words[0]);
    }catch(err){
        res.status(500).json({message:'خطا در سرور',error:err.message});
    }
};
exports.saveWord=async(req,res)=>{
    try{
        const{userId,wordId}=req.body;
        const user =await User.findByIdAndUpdate(
            userId,
            {$addToSet:{savedWords:wordId}},
            {new:true}

        );
        if(!user) return res.status(404).json({message:'کاربر پیدا نشد'});
        res.status(200).json({message:'لغت با موفقیت ذخیره شد',savedWords:user.savedWords});
    }catch(err){
        res.status(500).json({message:'خطا در سرور',error:err.message});
    }
};
exports.getSavedWords=async(req,res)=>{
    try{
        const userId=req.params.userId;
        const user=await User.findById(userId).populate('savedWords');
        if(!user)return res.status(404).json({message:'کاربر پیدا نشد'});
        res.status(200).json({count:user.savedWords.length,words:user.savedWords});
    }catch(err){
        res.status(500).json({message:'خطا در سرور',error:err.message});
    }
};
exports.getDashboardStats=async(req,res)=>{
    try{
        const userId=req.params.userId;
        const user=await User.findById(userId);
        if(!user)return res.status(404).json({message:'کاربر پیدا نشد'});
            res.status(200).json({
                level:user.level||1,
                streak:user.streak||0,
                savedWordsCount:user.savedWords.length
            });
        
    }catch(err){
        res.status(500).json({message:'خطا در سرور',error:err.message});
    }
};