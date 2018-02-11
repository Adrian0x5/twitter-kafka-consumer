package com.example.twitter.operations;

import com.example.twitter.service.DeleteEventService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class TweetsOperationsTest {

    private static final long TWEET_ID1 = 123L;
    private static final long USER_ID1 = 123L;
    private static final long TWEET_ID2 = 124L;
    private static final long USER_ID2 = 124L;
    private static final long TWEET_ID3 = 125L;
    private static final long USER_ID3 = 125L;

    @Mock
    private DeleteEventService deleteEventService;

    @InjectMocks
    private TweetsOperations victim;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        victim = new TweetsOperations();
        victim.setDeleteEventService(deleteEventService);
    }

    @Test
    public void testScript() {

    }


}