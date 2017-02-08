//Anita Paudel
//For this assignment, I have used Microsoft Visual Studio to complete this project.

#include <stdio.h>
#include "bstr.h"
#include "comp.h"

void COMP_Init(Computer *cmp) {
    int r, m;
    BSTR_SetValue(&(cmp->pc),0,16);
    BSTR_SetValue(&(cmp->ir),0,16);
    BSTR_SetValue(&(cmp->cc),0,3);
    for (r = 0; r < 8; r++) {
        BSTR_SetValue(&(cmp->reg[r]),r,16);  /* put some interesting data in registers */
    }
    for (m = 0; m < MAXMEM; m++) {
        BSTR_SetValue(&(cmp->mem[m]),0,16);
    }
}

void COMP_LoadWord(Computer* comp, int addr, BitString word) {
    comp->mem[addr] = word;
}


// TODO - Missing Piece: The Condition Code is not set.
void COMP_ExecuteNot(Computer *comp) {
    BitString drBS, srBS;
    BSTR_Substring(&drBS,comp->ir,4,3);
    BSTR_Substring(&srBS,comp->ir,7,3);
    comp->reg[ BSTR_GetValue(drBS) ] = comp->reg[ BSTR_GetValue(srBS) ];
    BSTR_Invert( & comp->reg[ BSTR_GetValue(drBS)  ]  );
	setConditionCode(comp, drBS);
}
void COMP_Execute(Computer* comp) {
	BitString opCode;
	int opCodeInt;
	for (int i = 0; i < MAXMEM; i++) {
		comp->ir = comp->mem[BSTR_GetValue(comp->pc)];
		BSTR_Substring(&opCode, comp->mem[BSTR_GetValue(comp->pc)], 0, 16);
		BSTR_AddOne(&comp->pc);
		BSTR_Substring(&opCode, comp->ir, 0, 4);  /* isolate op code */
		opCodeInt = BSTR_GetValue(opCode); /* get its value */

										   /*what kind of instruction is this? */
		if (opCodeInt == 9) {   // NOT
			COMP_ExecuteNot(comp);
		}
		if (opCodeInt == 0) {   // Branch
			COMP_ExecuteBR(comp);
		}
		if (opCodeInt == 1) {   // ADD
			COMP_ExecuteAdd(comp);
		}
		if (opCodeInt == 2) {   // Load
			COMP_ExecuteLD(comp);
		}
		if (opCodeInt == 15) {   // Trap
			COMP_ExecuteTRAP(comp);
		}
	}
}

void COMP_Display(Computer cmp) {
    int r, m;
    printf("\n");
    
    printf("PC ");
    BSTR_Display(cmp.pc,1);
    printf("   ");
    
    
    printf("IR ");
    BSTR_Display(cmp.ir,1);
    printf("   ");
    
    
    printf("CC ");
    BSTR_Display(cmp.cc,1);
    printf("\n");
    
    
    for (r = 0; r < 8; r++) {
        printf("R%d ",r);
        BSTR_Display(cmp.reg[r], 1);
        if (r % 3 == 2)
            printf("\n");
        else
            printf("   ");
    }
    printf("\n");
    for (m = 0; m < MAXMEM; m++) {
        printf("%3d ",m);
        BSTR_Display(cmp.mem[m], 1);
        
        if (m % 3 == 2)
            printf("\n");
        else
            printf("    ");
    }
    printf("\n");
}

void setConditionCode(Computer *comp, BitString DesValue) {
	BitString zero, negative;
	BSTR_Substring(&negative, DesValue, 0, 1);
	BSTR_Substring(&zero, DesValue, 0, 16);
	int value = BSTR_GetValue(negative);
	int zeroValue = BSTR_GetValue(zero);
	if (value) {
		BSTR_SetValue(&comp->cc, 4, 3);
	}
	else if (zeroValue == 0) {
		BSTR_SetValue(&comp->cc, 2, 3);
	}
	else {
		BSTR_SetValue(&comp->cc, 1, 3);
	}
}

void COMP_ExecuteTRAP(Computer* comp) {
	
	BitString trapValue, R0Input;
	int inputIn2sComp, trapVector;
	BSTR_Substring(&R0Input, comp->reg[0], 0, 16);
	BSTR_Substring(&trapValue, comp->ir, 8, 8);
	inputIn2sComp = BSTR_GetValueTwosComp(R0Input);
	trapVector = BSTR_GetValue(trapValue);
	if (trapVector == 37) {
		return;
	}
	else if (trapVector == 33) {
		//while (inputIn2sComp > 47) {
			printf("%c ", inputIn2sComp);
			//inputIn2sComp--;
		}
}

void COMP_ExecuteAdd(Computer *comp) {
	BitString drBS, sr1BS, sr2BS, accessMode, numValue, sum, posOrNeg;
	int immediateMode = 0;
	int posOrNegative = 0;
	int totalSum = 0;
	int immediateValue = 0; 


	BSTR_Substring(&drBS, comp->ir, 4, 3);
	BSTR_Substring(&sr1BS, comp->ir, 7, 3);
	BSTR_Substring(&accessMode, comp->ir, 10, 1);
	immediateMode = BSTR_GetValue(accessMode);
	BSTR_Substring(&posOrNeg, comp->ir, 11, 1);
	posOrNegative = BSTR_GetValue(posOrNeg);
	BSTR_Substring(&numValue, comp->ir, 11, 5);
	immediateValue = BSTR_GetValue(numValue);
	BSTR_Substring(&sr2BS, comp->ir, 13, 3);
	if (posOrNegative && immediateMode) { // if the number is negative take the 2's complement to add
		BSTR_Invert(&numValue);
		BSTR_AddOne(&numValue);
		immediateValue = BSTR_GetValue(numValue);
		immediateValue = -immediateValue;
	}
	
	if (immediateMode) { // If immediate mode
		totalSum = BSTR_GetValueTwosComp(comp->reg[BSTR_GetValue(sr1BS)]) + immediateValue;
		BSTR_SetValueTwosComp(&sum, totalSum, 16);
		comp->reg[BSTR_GetValue(drBS)] = sum;
	}
	else {//If not immediate mode
		totalSum = BSTR_GetValueTwosComp(comp->reg[BSTR_GetValue(sr1BS)])
			+ BSTR_GetValueTwosComp(comp->reg[BSTR_GetValue(sr2BS)]);
		BSTR_SetValue(&sum, totalSum, 16);
		comp->reg[BSTR_GetValue(drBS)] = sum;
	}
	setConditionCode(comp, sum);
}
void COMP_ExecuteLD(Computer *comp) {
	BitString drBs, PCOffset;
	BSTR_Substring(&drBs, comp->ir, 4, 3);
	BSTR_Substring(&PCOffset, comp->ir, 7, 9);
	int value = BSTR_GetValue(comp->pc);
	int offset = BSTR_GetValue(PCOffset);
	if (offset >= 256) {
		BSTR_Invert(&PCOffset);
		BSTR_AddOne(&PCOffset);
		offset = BSTR_GetValue(PCOffset);
		offset = -offset;
	}

	int address = offset + value;
	if (address > MAXMEM) {
		return;

	}

	comp->reg[BSTR_GetValue(drBs)] = comp->mem[offset + value];
	BitString registerValue = comp->mem[offset + value];
	setConditionCode(comp, registerValue);
	
}
void COMP_ExecuteBR(Computer *comp) {
	BitString offset, dest;
	int condVal, condition, checkBr = 0, offsetPc;
	BSTR_Substring(&dest, comp->ir, 4, 3);
	condVal = BSTR_GetValue(dest);
	condition = BSTR_GetValueTwosComp(comp->cc);
	// if any of n,z,p is 1, and equal to cc then true
	if (dest.bits[0] == 1 && condition == 4) checkBr = 1;
	if (dest.bits[1] == 1 && condition == 2) checkBr = 1;
	if (dest.bits[2] == 1 && condition == 1) checkBr = 1;
	// if nzp all positve or zero
	if (condVal == 7 || condVal == 0 || checkBr) {
		BSTR_Substring(&offset, comp->ir, 7, 9);
		offsetPc = BSTR_GetValue(comp->pc) + BSTR_GetValueTwosComp(offset);
		BSTR_SetValueTwosComp(&comp->pc, offsetPc, 16);
	}
	setConditionCode(comp, dest);
}


