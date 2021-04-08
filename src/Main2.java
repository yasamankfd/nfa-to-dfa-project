import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
class findState
{
    public static boolean find(State[] list , String name,int size)
    {
        boolean exist = false;
        for(int i=0;i<size;i++)
        {
            if((list[i].name).equals(name))
            {
                exist=true;
                break;
            }
        }
        return exist;
    }

    public static int find2(State[] list , String name,int size)
    {
        int exist = 0;
        for(int i=0;i<size;i++)
        {
            if((list[i].name).equals(name))
            {
                exist=i;
                break;
            }
        }
        return exist;
    }
}
public class Main2 {
    public static void main(String[] args) {
        int numOfStates;
        System.out.print("enter the number of states :");
        Scanner s = new Scanner(System.in);
        numOfStates = s.nextInt();
        int[][][] nfa = new int[numOfStates][numOfStates][2];
        for (int i=0; i<numOfStates;i++)
        {
            for (int j=0 ; j<numOfStates ; j++)
            {
                for (int k=0;k<2;k++)
                {
                    nfa[i][j][k]=-1;
                }
            }
        }
        System.out.print("enter number of edges :");
        int numOEdges;
        int[] finals = new int[numOfStates];
        numOEdges = s.nextInt();
        System.out.println("enter starting state , destination state , input and then enter '1' if its final , '0' if its not :");
        for( int i = 0; i<numOEdges ; i++)
        {
            System.out.println("edge "+(i+1));
            int x,y;
            System.out.print("starting state :");
            x = s.nextInt();
            System.out.print("destination state :");
            y = s.nextInt();
            System.out.print("input value :");
            int input = s.nextInt();
            if(input==0)
            {
                nfa[x][y][0]=1;
            }else nfa[x][y][1]=1;
            System.out.print("final or not final :");
            finals[x] = s.nextInt();
        }
        System.out.print("enter starting state :");
        int startingState ;
        startingState = s.nextInt();
        Queue queue = new LinkedList();
        State[] dfa = new State[1000];
        dfa[0] = new State();
        dfa[0].name = startingState +",";
        dfa[0].index = 0;
        int index=0;
        queue.add(dfa[0]);
        while ((queue.size()>0))
        {
            String name ="";
            State thisState = (State) queue.remove();
            int thisIndex = thisState.index;
            String[] stateName = thisState.name.split(",");
            boolean isfinal = false;
            boolean for1=false,for0=false;

            for (String m: stateName)
            {
                if(!m.equals(""))
                {
                    int i = Integer.parseInt(m);
                    for(int j=0;j<numOfStates;j++)
                    {
                        if (nfa[i][j][1]==1 && !name.contains(Integer.toString(j)))
                        {
                            name+=j+",";
                            if(finals[i]==1)
                            {
                                isfinal=true;
                            }
                        }
                    }
                }
            }
            if(name.equals("")) { }
            else{
            for1=true;
            String[] names = name.split(",");
            int[] namesInInteger = new int[names.length];
            Arrays.fill(namesInInteger,0);
            int k = 0 ;
            for (String s1: names) {
                if(!s1.equals(""))
                {namesInInteger[k] = Integer.parseInt(s1);
                    k++;}
            }
            name="";
            Arrays.sort(namesInInteger);
            for (int i1: namesInInteger) {
                name += i1+",";
            }
            index++;
            if(!findState.find(dfa,name,index))
            {
                dfa[index] = new State();
                dfa[index].name = name;
                dfa[index].forOne = null;
                dfa[index].forZero = null;
                dfa[index].isfinal=isfinal;
                dfa[index].index = index;
                dfa[thisIndex].forOne = dfa[index];
                queue.add(dfa[index]);
            }else
            {

                dfa[thisIndex].forOne=dfa[findState.find2(dfa,name,index+1)];
                index--;
            }
        }
            name="";
            isfinal=false;
            for (String m: stateName)
            {
                if(!m.equals(""))
                {
                    int i = Integer.parseInt(m);
                    for(int j=0;j<numOfStates;j++)
                    {
                        if (nfa[i][j][0]==1 && !name.contains(Integer.toString(j)))
                        {
                            name+=j+",";
                            if(finals[i]==1)
                            {
                                isfinal=true;
                            }
                        }
                    }
                }
            }
            if(name.equals("")) { }
            else{
                    for0=true;
                     String[] names1 = name.split(",");
                    int[] namesInInteger1 = new int[names1.length];
                    Arrays.fill(namesInInteger1,0);
                    int k = 0 ;
                    for (String s1: names1)
                    {
                        if(!s1.equals(""))
                        {
                            namesInInteger1[k] = Integer.parseInt(s1);
                            k++;
                        }
                    }
                    name = "";
                    Arrays.sort(namesInInteger1);
                    for (int i1: namesInInteger1)
                    {
                        name +=  i1+",";
                    }
                    index++;
                    if(!findState.find(dfa,name,index))
                    {
                        dfa[index] = new State();
                        dfa[index].name = name;
                        dfa[index].forOne = null;
                        dfa[index].forZero = null;
                        dfa[index].index = index;
                        dfa[index].isfinal=isfinal;
                        dfa[thisIndex].forZero = dfa[index];
                        queue.add(dfa[index]);
                    }else
                        {

                            dfa[thisIndex].forZero=dfa[findState.find2(dfa,name,index+1)];
                            index--;
                        }
                }
            if(!for0)
            {
                index++;
                if(!findState.find(dfa,"trap"+thisState.name,index))
                {
                    dfa[index] = new State();
                    dfa[index].name = "trap"+thisState.name;
                    dfa[index].forOne = dfa[index];
                    dfa[index].forZero = dfa[index];
                    dfa[index].index = index;
                    dfa[index].isfinal=false;
                    dfa[thisIndex].forZero = dfa[index];
                }else{ index--; dfa[thisIndex].forZero=dfa[findState.find2(dfa,"trap"+thisState.name,index)];}
            }
            if(!for1)
            {
                index++;
                if(!findState.find(dfa,"trap"+thisState.name,index))
                {
                    dfa[index] = new State();
                    dfa[index].name = "trap"+thisState.name;
                    dfa[index].forOne = dfa[index];
                    dfa[index].forZero = dfa[index];
                    dfa[index].index = index;
                    dfa[index].isfinal=false;
                    dfa[thisIndex].forOne = dfa[index];
                }else{ index--; dfa[thisIndex].forOne=dfa[findState.find2(dfa,"trap"+thisState.name,index)]; }
            }
        }
        System.out.println("all states :--------------------------");
        for(int h=0;h<=index;h++)
        {
            System.out.println(dfa[h].name+" -- "+dfa[h].isfinal);
        }
        System.out.println("----------------------");
        System.out.println("\n\n\norder is : starting state  --> for 0 destiation state name | --> for 1 destiation state name     is final or not");
        for (int i=0;i<index+1;i++)
        {
            System.out.println(dfa[i].name+"  --> '0'   "+dfa[i].forZero.name+" | "+"--> '1'   "+dfa[i].forOne.name+"            "+dfa[i].isfinal);
        }
        int l;
        System.out.println("input anything to finish:");
        l = s.nextInt();
    }


}
