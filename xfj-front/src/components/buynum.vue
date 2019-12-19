<template>
  <!--数量-->
  <div class="item-cols-num clearfix">
    <div class="select">
      <span class="down"
            @click.stop.prevent="down()"
            :class="{'down-disabled':Num<=1}">-
      </span>
      <span class="num">
        <input type="text"
               :class="{show:show}"
               v-model="Num>=limit?limit:Num"
               @blur="blur()"
               maxlength="2">
                  <ul ref="ul">
                    <li v-for="i in numList" :key="i">{{i}}</li>
                  </ul>
      </span>
      <span class="up" :class="{'up-disabled':Num>=limit}"
            @click.stop.prevent="up()">+</span>
    </div>
  </div>
</template>
<script src="../js/components/buynum.js"></script>
<style src="../css/components/buynum.scss" lang="scss" scoped></style>
