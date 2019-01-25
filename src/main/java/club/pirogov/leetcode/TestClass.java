package club.pirogov.leetcode;

public class TestClass {
    public static int[] twoSum(int[] nums, int target) {
        int returnArr[]= {-1,-1};
        
        for (int i=0;i<nums.length;i++){
            for (int j=0;i<nums.length;j++){
                if (i==j){continue;}
                if ((nums[i]+nums[j])==target){
                    returnArr[0]=i;
                    returnArr[1]=j;
                    break;
                }
            }
            if (returnArr[0]!=-1){break;}
        }
        return returnArr;
    }
    
    public static void main(String[] args) {
    	int arrToSend[] = {1,2,3,4,5,6};
    	
    	System.out.println(twoSum(arrToSend,7)[0]);
    }
}