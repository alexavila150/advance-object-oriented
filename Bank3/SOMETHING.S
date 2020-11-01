Typedef struct tnode_s {
    struct tnode_s *children[3];
    short val;
} Tnode;


treeSum:
	sub #8, r1		; allocate memory
	mov r12, 0(r1)

	short sum = 0;
	if (p) goto endif:
	Tnode **pp = p->children;
	char j = 0;

loop:
	if(j => 3) goto endloop:
	sum += treeSum(*pp);
	pp += 1;
	j += 1;
	goto loop:

endloop:
	sum += p->val;

endif:
	return sum;

