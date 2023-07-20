import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LeetCodeTest {

    public static void main(String[] args) {
        LeetCodeTest t = new LeetCodeTest();
        // 字符串相加
        System.out.println(t.test1("123", "45"));
        // 两数之和
        System.out.println(Arrays.toString(t.test2(new int[]{2, 7, 11, 15}, 9)));
        //
        t.t();
    }

    public String test1(String num1, String num2) {
        int n1 = num1.length() - 1;
        int n2 = num2.length() - 1;
        int add = 0;
        StringBuffer s = new StringBuffer();
        while (n1 >= 0 || n2 >= 0 || add != 0) {
            int a = n1 >= 0 ? num1.charAt(n1) - '0' : 0;
            int b = n2 >= 0 ? num2.charAt(n2) - '0' : 0;
            int c = a + b + add;
            s.append(c % 10);
            add = c / 10;
            n1--;
            n2--;
        }
        return s.reverse().toString();
    }


    public int[] test2(int[] nums, int target) {
        for (int i = 0; i < nums.length; ++i) {
            for (int j = i + 1; j < nums.length; ++j) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 计算的结果链表
        ListNode head = null;
        ListNode tail = null;
        int add = 0;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + add ;
            if(head == null){
                head = tail = new ListNode(sum % 10);
            }else{
                tail.next = new ListNode( sum % 10);
                tail = tail.next;
            }
            add =  sum / 10;
            if(l1 != null){
                l1 = l1.next;
            }
            if(l2 != null){
                l2 = l2.next;
            }
        }
        if(add > 0){
            tail.next = new ListNode(add);
        }
        return head;
    }

    public ListNode Merge(ListNode list1,ListNode list2) {
        if(list1 == null || list2 == null){
            return list1 != null ? list1 : list2;
        }
        if(list1.val <= list2.val){
            list1.next = Merge(list1.next, list2);
            return list1;
        }else{
            list2.next = Merge(list1, list2.next);
            return list2;
        }
    }

    public void t (){
        ListNode head = null;
        ListNode tail = null;
        head = tail  = new ListNode(1);
        head.next = new ListNode(2);
        System.out.println(tail.val + "--" + tail.next.val);
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
