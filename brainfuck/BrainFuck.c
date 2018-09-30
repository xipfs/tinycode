/*
 ============================================================================
 Name        : BrainFuck.c
 Author      : xiehui
 Version     : 1.0
 Email       : flexie@foxmail.com
 Copyright   : Copyright © 2018 XIPFS Services. All rights reserved.
 Description : BrainFuck 语法规则:
			   > 	下一单元作为当前的数据单元
			   <	上一单元作为当前的数据单元
			   + 	当前数据单元的值加1
			   -	当前数据单元的值减1
			   .	当前数据单元的值作为字符输出（ascii 码）
			   [	如果当前数据单元的值为零，下一条指令在对应的  ]  后
			   ]	如果当前数据单元的值不为零，下一条指令在对应的  [ 后
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 定义求数组长度宏
#define ARRAY_SIZE(x) (sizeof(x)/sizeof((x)[0]))

// 定义输入指令(相当于图灵机的程序)
char code[] = "++++++++++[>+>+++>+++++++>++++++++++<<<<-]>>>++.>+.+++++++..+++.<<++.>+++++++++++++++.>.+++.------.--------.<<+.<.";

// 获取指令的长度
int length = ARRAY_SIZE(code);

// 分配并且初始化一块数据空间(相当于图灵机的纸带)
int buffer[100] ={0};

// 当前代码执行位置(相当于图灵机的控制器)
int p=0;

// 当前写入纸带位置(相当于图灵机的控制器)
int q=0;

// 处理代码跳转
void loop(int inc);

int main(void) {
	// 一直运行,直到代码全部执行完毕.
	while(p < length){
		switch(code[p]){
		case '>':
			q++;
			break;
		case '<':
			q--;
			break;
		case '+':
			buffer[q]++;
			break;
		case '-':
			buffer[q]--;
			break;
		case '.':
			printf("%c",buffer[q]);
			break;
		case '[':
			if(buffer[q] ==0){
				loop(1);
			}
			break;
		case ']':
			if(buffer[q] !=0){
				loop(-1);
			}
			break;
		default:
			break;
		}
		// 执行下一条指令
		p++;
	}
	return EXIT_SUCCESS;
}

void loop(int inc){
	int i;
	for(i = inc; i !=0 ; p += inc){
		switch(code[p+inc]){
		case '[':
			i++;
			break;
		case']':
			i--;
			break;
		default:
			break;
		}
	}
}


