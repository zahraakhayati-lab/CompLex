const mongoose = require('mongoose');
const userSchema=new mongoose.Schema({
    name:{
        type:String,
        required:[true,'وارد کردن نام الزامی است']
    },
    email:{
        type:String,
        required:[true,'وارد کردن ایمیل الزامی است'],
        unique:true,
        lowercase:true
    },
    password:{
        type:String,
        required:[true,'وارد کردن رمز عبور الزامی است'],
        minlength:6
    },
    savedWords:[{
        type:mongoose.Schema.Types.ObjectId,
        ref:'Word'
    }]
},{timestamps:true});
module.exports=mongoose.model('User',userSchema);