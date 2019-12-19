<template>
  <input :type="inputType"
         readonly
         :class="classStyle"
         @click="btnClick($event)"
         :disabled="classStyle==='disabled-btn'"
         :value="text">
</template>
<script src="../js/components/ybutton.js"></script>
<style src="../css/components/ybutton.scss" lang="scss" rel="stylesheet/scss" scoped></style>
