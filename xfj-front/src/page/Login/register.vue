<template>
  <div class="login v2">
    <div class="wrapper">
      <div class="dialog dialog-shadow" style="display: block; margin-top: -362px;">
        <div class="registered">
          <h4>注册 xfj-mall 账号</h4>
          <div class="content" style="margin-top: 20px;">
            <ul class="common-form">
              <li class="username border-1p">
                <div style="margin-top: 40px;" class="input">
                  <input type="text"
                         v-model="registered.userName" placeholder="账号"
                         @keyup="registered.userName=registered.userName.replace(/[^\w\.\/]/ig,'')">
                </div>
              </li>
              <li>
                <div class="input">
                  <input type="password"
                         v-model="registered.userPwd"
                         placeholder="密码">
                </div>
              </li>
              <li>
                <div class="input">
                  <input type="password"
                         v-model="registered.userPwd2"
                         placeholder="重复密码">
                </div>
              </li>
              <li>
                <div class="input">
                  <input type="text" v-model="registered.captcha" placeholder="验证码" style="width:650px;"/>
                  &nbsp;&nbsp;&nbsp;
                  <img id="imageCode" :src="imageCode" @click="init_geetest()"/>
                </div>
              </li>
            </ul>
            <el-checkbox class="agree" v-model="agreement">
              我已阅读并同意遵守
              <a @click="open('法律声明','此站点仅作为学习使用，承担不起任何法律问题')">法律声明</a> 和
              <a @click="open('隐私条款','本网站将不会严格遵守有关法律法规和本隐私政策所载明的内容收集、使用您的信息')">隐私条款</a>
            </el-checkbox>
            <div style="margin-bottom: 30px;">
              <y-button
                :classStyle="registered.userPwd&&registered.userPwd2&&registered.userName&&registered.captcha&&registxt==='注册'?'main-btn':'disabled-btn'"
                :text="registxt"
                style="margin: 0;width: 100%;height: 40px;font-size: 15px;line-height: 40px"
                @btnClick="regist"
              >
              </y-button>
            </div>
            <div class="border" style="margin-bottom: 10px;"></div>
            <ul class="common-form pr">
              <!-- <li class="pa" style="left: 0;top: 0;margin: 0;color: #d44d44">{{registered.errMsg}}</li> -->
              <li style="text-align: center;line-height: 48px;margin-bottom: 0;font-size: 15px;color: #999;">
                <span>如果您已拥有 <span style="color:royalblue">xfj-mall</span> 账号，则可在此</span>
                <a href="javascript:;"
                   style="margin: 0 5px"
                   @click="toLogin">登录</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script src="../../../static/geetest/gt.js"></script>
<script src="../../js/login/register.js"></script>
<style src="../../css/login/register.scss" lang="scss" rel="stylesheet/scss" scoped></style>

